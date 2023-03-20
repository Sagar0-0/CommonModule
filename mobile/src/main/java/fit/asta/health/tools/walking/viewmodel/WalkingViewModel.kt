package fit.asta.health.tools.walking.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
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
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WalkingViewModel
@Inject constructor(
    private val walkingToolRepo: WalkingToolRepo, private val stepsSensor: MeasurableSensor
) : ViewModel() {

    private val _homeUiState = mutableStateOf(HomeUIState())
    val homeUiState: State<HomeUIState> = _homeUiState

    private val _apiState = mutableStateOf(WalkingTool())
    val ApiState: State<WalkingTool> = _apiState

    init {
        loadWalkingToolData()
    }

    private fun loadWalkingToolData() {
        viewModelScope.launch {
            walkingToolRepo.getHomeData(userId = "6309a9379af54f142c65fbfe")
                .collect {
                when(it){
                    is NetworkResult.Loading -> {}
                    is NetworkResult.Success -> {
                        _apiState.value= it.data!!
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

    val initialStep = MutableStateFlow(true)
    fun onGoalSelected(goal: List<String>) {
        _selectedGoal.value = goal
    }

    fun onWalkTypesSelected(walkTypes: String) {
        _selectedWalkTypes.value = walkTypes
    }


    private val _uiState = mutableStateOf(StepCounterUIState())
    val uiState: State<StepCounterUIState> = _uiState

    fun onUIEvent(uiEvent: StepCounterUIEvent) {
        when (uiEvent) {
            is StepCounterUIEvent.StartButtonClicked -> startSteps()
            is StepCounterUIEvent.StopButtonClicked -> stopSteps()
            else -> {}
        }
    }


    private fun startSteps() {
        setStartTime()
        stepsSensor.startListening()
        stepsSensor.setOnSensorValuesChangedListener { step ->

            if (initialStep.value) {
                _uiState.value = _uiState.value.copy(initialSteps = step[0].toInt())
                initialStep.value = false
            }
            val steps = step[0].toInt() - _uiState.value.initialSteps
            val stepLength = 0.7f
            val timeNs = System.nanoTime() - _uiState.value.startTime
            val speed = calculateSpeed(steps, stepLength, timeNs)

            _uiState.value = _uiState.value.copy(
                steps = steps,
                distance = (step[0].toInt() / 1408),
                speed = speed
            )
            _homeUiState.value = _homeUiState.value.copy(
                distance = (step[0].toInt() / 1408), steps = steps
            )
        }
    }


    fun setStartTime() {
        _uiState.value = _uiState.value.copy(startTime = System.nanoTime())
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

}