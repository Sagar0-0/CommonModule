package fit.asta.health.tools.walking.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.sensor.MeasurableSensor
import fit.asta.health.tools.walking.view.home.HomeUIState
import fit.asta.health.tools.walking.view.home.StepCounterUIEvent
import fit.asta.health.tools.walking.view.steps_counter.StepCounterUIState
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
    private val stepsSensor: MeasurableSensor,
    private val localRepo: LocalRepo
) : ViewModel() {
    val date = LocalDate.now().dayOfMonth
    private val _homeUiState = mutableStateOf(HomeUIState())
    val homeUiState: State<HomeUIState> = _homeUiState

    private val _apiState = mutableStateOf(WalkingTool())
    val ApiState: State<WalkingTool> = _apiState

    val startWorking= mutableStateOf(false)

    init {
        loadWalkingToolData()

        viewModelScope.launch {
            val date = localRepo.getStepsData(LocalDate.now().dayOfMonth)
            if (date == null) {
                val stepsData = StepsData(
                    date = LocalDate.now().dayOfMonth, status = "",
                    initialSteps = 0, allSteps = 0,
                    time = 0
                )
                localRepo.insert(stepsData)
            }
            if (date != null) {
                if (date.date==LocalDate.now().dayOfMonth){

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
                startSteps()
                startWorking.value=true
            }
            is StepCounterUIEvent.StopButtonClicked -> {
                startWorking.value=false
                stopSteps()
                changeStatus("inactive")
                endScreen()
            }
            else -> {}
        }
    }


    private fun startSteps() {
        stepsSensor.startListening()
        stepsSensor.setOnSensorValuesChangedListener { step ->
            Log.d("Subhash", "start:${step[0].toInt()}")
            Log.d("Subhash", "out:  $date")
            viewModelScope.launch {
                val data = localRepo.getStepsData(date)
                if (data != null && data.date == date) {
                    if (data.initialSteps > 1) {
                        localRepo.updateStepsonRunning(
                            date = data.date,
                            all_steps = step[0].toInt() - data.initialSteps,
                        )
                        Log.d(
                            "Subhash",
                            "update:  steps distance${((step[0].toInt() - data.initialSteps))}"
                        )
                    } else {
                        Log.d("Subhash", "startSteps: start steps")
                        localRepo.updateSteps(date = date, step = step[0].toInt())
                    }
                    _homeUiState.value = _homeUiState.value.copy(
                        distance = (data.allSteps / 1408),
                        steps = data.allSteps,
                        duration = (System.nanoTime() - data.time).toInt()
                    )
                } else {
                    Log.d("Subhash", "startSteps: null")
                }
            }
        }
    }




    private fun stopSteps() {
        stepsSensor.stopListening()
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


            } catch (e: Exception) {

            }
        }
    }

    private val _uiStateStep = mutableStateOf(StepCounterUIState())
    val uiStateStep: State<StepCounterUIState> = _uiStateStep
    fun endScreen() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(date)
            if (data != null && data.date == date) {
                val steps = data.allSteps
                val stepLength = 0.7f
                val timeNs = System.nanoTime() - data.time
                val speed = calculateSpeed(steps, stepLength, timeNs)
                val distance = ((data.allSteps - data.initialSteps) / 1408)
                _uiStateStep.value = _uiStateStep.value.copy(
                    calories = 0, steps = steps,
                    distance = distance, speed = speed, time = timeNs
                )
            }
        }
    }

    fun changeStatus(status:String){
        viewModelScope.launch {
            localRepo.updateStatus(date, status)
        }
    }

    fun changeTime(){
        viewModelScope.launch {
            localRepo.updateTime(date,System.nanoTime().toInt())
        }
    }
}