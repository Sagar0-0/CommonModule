package fit.asta.health.tools.walking.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.request.*
import fit.asta.health.tools.walking.model.network.request.Target
import fit.asta.health.tools.walking.view.home.HomeUIState
import fit.asta.health.tools.walking.view.home.StepCounterUIEvent
import fit.asta.health.tools.walking.view.steps_counter.StepCounterUIState
import fit.asta.health.tools.walking.work.CountStepsService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WalkingViewModel
@Inject constructor(
    private val walkingToolRepo: WalkingToolRepo,
    private val localRepo: LocalRepo
) : ViewModel() {
    val date = LocalDate.now().dayOfMonth
    private var serviceIntent: Intent? = null
    private val _homeUiState = mutableStateOf(HomeUIState())
    val homeUiState: State<HomeUIState> = _homeUiState

    private val _apiState = mutableStateOf(WalkingTool())
    val ApiState: State<WalkingTool> = _apiState

    val startWorking = mutableStateOf(false)

    init {
        loadWalkingToolData()
        viewModelScope.launch {
            val date = localRepo.getStepsData(LocalDate.now().dayOfMonth)
            if (date == null) {
                val stepsData = StepsData(
                    date = LocalDate.now().dayOfMonth, status = "",
                    initialSteps = 0, allSteps = 0, time = 0,
                    realtime = 0, distanceRecommend = 0.0, durationRecommend = 0,
                    distanceTarget = 0.0, durationTarget = 0
                )
                localRepo.insert(stepsData)
            } else {
                if (date.status == "active") {
                    startWorking.value = true
                    _homeUiState.value = _homeUiState.value.copy(
                        distance = (date.allSteps / 1408).toDouble(),
                        steps = date.allSteps,
                        duration = ((System.nanoTime() - date.time) / 1_000_000_000L / 60L)
                    )
                }
            }
        }
    }

    private fun loadWalkingToolData() {
        viewModelScope.launch {
            walkingToolRepo.getHomeData(userId = "6309a9379af54f142c65fbfe")
                .collect {
                    when (it) {
                        is NetworkResult.Loading -> {}
                        is NetworkResult.Success -> {
                            _apiState.value = it.data!!
                            val date = localRepo.getStepsData(LocalDate.now().dayOfMonth)
                            if (date != null) {
                                localRepo.updateTargetAndRecommend(
                                    date = date.date,
                                    distanceRecommend = it.data.distanceRecommend,
                                    durationRecommend = it.data.durationRecommend,
                                    distanceTarget = it.data.distanceTarget,
                                    durationTarget = it.data.durationTarget
                                )
                            }
                        }
                        is NetworkResult.Error -> {}
                        else -> {}
                    }
                }
        }
    }


    private val _selectedGoal = MutableStateFlow(emptyList<String>())
    val selectedGoal: StateFlow<List<String>> = _selectedGoal

    private val _selectedWalkTypes = MutableStateFlow("")
    val selectedWalkTypes: StateFlow<String> = _selectedWalkTypes

    fun onGoalSelected(goal: List<String>) {
        _selectedGoal.value = goal
    }

    fun onWalkTypesSelected(walkTypes: String) {
        _selectedWalkTypes.value = walkTypes
    }


    fun onUIEvent(uiEvent: StepCounterUIEvent) {
        when (uiEvent) {
            is StepCounterUIEvent.StartButtonClicked -> {
                changeStatus("active")
                changeTime()
                startWorking.value = true
            }
            is StepCounterUIEvent.StopButtonClicked -> {
                startWorking.value = false
                changeStatus("inactive")
                endScreen()
            }
            is StepCounterUIEvent.PutDataButtonClicked -> {
                putDataToServer()
            }
        }
    }


    private fun calculateSpeed(steps: Int, stepLength: Float, timeNs: Long): Float {
        val distance = steps * stepLength
        val timeSeconds = timeNs / 1_000_000_000L
        return if (timeSeconds == 0L) {
            0f
        } else {
            val speed = distance / timeSeconds
            speed * 3.6f // convert meters per second to kilometers per hour
        }
    }


    fun putDataToServer() {
        viewModelScope.launch {
            try {
                val result = walkingToolRepo.putData(setData())
                Log.d("TAG", "putDataToServer: ${result.status.msg}")
            } catch (e: Exception) {
                Log.d("TAG", "putDataToServer error: ${e.message}")
            }
        }
    }

    private val _uiStateStep = mutableStateOf(StepCounterUIState())
    val uiStateStep: State<StepCounterUIState> = _uiStateStep
    private fun endScreen() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(date)
            if (data != null && data.date == date) {
                val steps = data.allSteps
                val stepLength = 0.7f
                val timeNs = (System.nanoTime() - data.time)
                val speed = calculateSpeed(steps, stepLength, timeNs)
                val distance = (data.allSteps / 1408)
                _uiStateStep.value = _uiStateStep.value.copy(
                    calories = 0, steps = steps,
                    distance = distance, speed = speed,
                    time = (timeNs / 1_000_000_000L / 60L)
                )
            }
        }
    }

    private fun changeStatus(status: String) {
        viewModelScope.launch {
            localRepo.updateStatus(date, status)
        }
    }

    private fun changeTime() {
        viewModelScope.launch {
            localRepo.updateTime(date, System.nanoTime())
        }
    }

    fun startService(context: Context) {
        if (serviceIntent == null) {
            serviceIntent = Intent(context, CountStepsService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent!!)
        }
    }

    fun stopService(context: Context) {
        if (serviceIntent != null) {
            context.stopService(serviceIntent!!)
            serviceIntent = null
        }
    }

    fun changeUi(data: HomeUIState) {
        _homeUiState.value = data
    }

    fun setData(): PutData {
        return PutData(
            code = 3,
            id = "6309a9379af54f142c65fbfe",
            name = "walking",
            sType = 1,
            uid = "6309a9379af54f142c65fbfe",
            wea = true,
            tgt = Target(
                dis = Distance(
                    dis = 2,
                    unit = "km"
                ),
                dur = Duration(
                    dur = 20,
                    unit = "mins"
                ),
                steps = Steps(
                    steps = 2000,
                    unit = "steps"
                )
            ),
            prc = listOf(
                Prc(
                    id = "",
                    ttl = "mode",
                    dsc = "mode",
                    values = listOf(
                        Value(
                            id = "",
                            name = "indoor",
                            value = "indoor"
                        )
                    )
                ),
                Prc(
                    id = "",
                    ttl = "music",
                    code = "music_tool",
                    dsc = "music",
                    values = listOf(
                        Value(
                            id = "",
                            name = "spotify",
                            value = "spotify"
                        )
                    )
                ),
                Prc(
                    id = "",
                    ttl = "goal",
                    dsc = "goal",
                    values = _selectedGoal.value.map {
                        Value(id = "", name = it, value = it)
                    }
                ),
                Prc(
                    id = "",
                    ttl = "type",
                    dsc = "type",
                    values = listOf(
                        Value(
                            id = "",
                            name = _selectedWalkTypes.value,
                            value = _selectedWalkTypes.value
                        )
                    )
                )
            )
        )

    }
}