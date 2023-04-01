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
        setupDatabaseAndUpdateUi()
        loadWalkingToolData()
    }

    private fun setupDatabaseAndUpdateUi() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(LocalDate.now().dayOfMonth)
            if (data == null) {
                val stepsData = StepsData(
                    date = LocalDate.now().dayOfMonth, status = "", initialSteps = 0,
                    allSteps = 0, time = 0, realtime = 0, distanceRecommend = 0.0,
                    durationRecommend = 0, distanceTarget = 0.0, durationTarget = 0,
                    id = "6427fc83baf761e33a2859ef", calories = 0.0, weightLoosed = 0.0, appliedAngleDistance = 0f,
                    appliedAngleDuration = 0f
                )
                localRepo.insert(stepsData)
            } else {
                if (data.status == "active") {
                    startWorking.value = true
                    _homeUiState.value = _homeUiState.value.copy(
                        distance = (data.allSteps / 1408).toDouble(),
                        steps = data.allSteps,
                        duration = ((System.nanoTime() - data.time) / 1_000_000_000L / 60L),
                        appliedAngleDuration = data.appliedAngleDuration,
                        appliedAngleDistance = data.appliedAngleDistance,
                        start = true
                    )
                }
            }
        }
    }

    private fun loadWalkingToolData() {
        viewModelScope.launch {
            walkingToolRepo.getHomeData(userId = "6383745840efcc8cdeb289e1")
                .collect {
                    when (it) {
                        is NetworkResult.Loading -> {}
                        is NetworkResult.Success -> {
                            _apiState.value = it.data!!
                            _homeUiState.value = _homeUiState.value.copy(
                                distanceTarget = it.data.distanceRecommend.toFloat(),
                                durationTarget = it.data.durationRecommend.toFloat()
                            )
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
                _homeUiState.value = _homeUiState.value.copy(start = true)
            }
            is StepCounterUIEvent.StopButtonClicked -> {
                endScreen()
            }
            is StepCounterUIEvent.PutDataButtonClicked -> {
                putDataToServer()
            }
            is StepCounterUIEvent.EndButtonClicked -> {
                startWorking.value = false
                _homeUiState.value = _homeUiState.value.copy(start = false)
                changeStatus("inactive")
            }
            is StepCounterUIEvent.ChangeTargetDuration -> {
                setTargetDuration(uiEvent.input)
            }
            is StepCounterUIEvent.ChangeTargetDistance -> {
                setTargetDistance(uiEvent.input)
            }
            is StepCounterUIEvent.ChangeAngelDuration->{
                _homeUiState.value=_homeUiState.value.copy(appliedAngleDuration = uiEvent.input)
            }
            is StepCounterUIEvent.ChangeAngleDistance->{
                _homeUiState.value=_homeUiState.value.copy(appliedAngleDistance = uiEvent.input)
            }

            else -> {}
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


    private fun putDataToServer() {
        viewModelScope.launch {
            try {
                val id = localRepo.getStepsData(date)
                val result = walkingToolRepo.putData(setData(id = id?.id ?: ""))
                localRepo.updateIdForPutData(date, id = result.data.id)
                Log.d("TAG", "putDataToServer: ${result.status.msg}")
                loadWalkingToolData()
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
                val calories = calculateCaloriesBurned(stepsCount = steps)
                val weight = calculateWeightLoss(calories)
                localRepo.updateCaloriesAndWeightLoosed(date, calories, weight)
                _uiStateStep.value = _uiStateStep.value.copy(
                    calories = calories, steps = steps,
                    distance = distance, speed = speed,
                    time = (timeNs / 1_000_000_000L / 60L),
                    weightLoosed = weight
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
        _homeUiState.value = _homeUiState.value.copy(
            distance = data.distance,
            steps = data.steps,
            duration = data.duration,
            valueDistanceRecommendation = data.valueDistanceRecommendation,
            valueDurationRecommendation = data.valueDurationRecommendation,
            valueDistanceGoal = data.valueDistanceGoal,
            valueDurationGoal = data.valueDurationGoal
        )
    }

    fun setData(id: String): PutData {
        Log.d("TAG", "setData: ${_homeUiState.value}")
        return PutData(
            code = 3,
            id = id,
            name = "walking",
            sType = 1,
            uid = "6383745840efcc8cdeb289e1",
            wea = true,
            tgt = Target(
                dis = Distance(
                    dis = _homeUiState.value.distanceTarget,
                    unit = "km"
                ),
                dur = Duration(
                    dur = _homeUiState.value.durationTarget.toInt(),
                    unit = "mins"
                ),
                steps = Steps(
                    steps = _homeUiState.value.distanceTarget.toInt() * 1408,
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


    fun calculateWeightLoss(caloriesBurned: Double): Double {
        return caloriesBurned / 7.7 // 7700 calories per kilogram of body fat
    }

    fun calculateCaloriesBurned(
        bodyWeight: Double = 50.0,
        distancePerStep: Double = 0.7,
        stepsCount: Int
    ): Double {
        val metersPerStep = distancePerStep * 0.3048 // convert feet to meters
        val caloriesPerKilogramPerMeter = 0.57 // average value for walking/running
        return bodyWeight * stepsCount * metersPerStep * caloriesPerKilogramPerMeter
    }

    fun setTargetDuration(durationTarget: Float) {
        _homeUiState.value = _homeUiState.value.copy(durationTarget = durationTarget)
        Log.d("TAG", "setTargetDuration: $durationTarget")
    }

    fun setTargetDistance(distanceTarget: Float) {
        _homeUiState.value = _homeUiState.value.copy(distanceTarget = distanceTarget)
        Log.d("TAG", "setTargetDistance: $distanceTarget")
    }
}