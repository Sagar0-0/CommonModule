package fit.asta.health.tools.breathing.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.breathing.model.BreathingRepo
import fit.asta.health.tools.breathing.view.home.UiEvent
import fit.asta.health.tools.breathing.view.home.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BreathingViewModel @Inject constructor(
    private val breathingRepo: BreathingRepo
) : ViewModel() {

    private val date = LocalDate.now().dayOfMonth
    private val _selectedExercise = MutableStateFlow(listOf("ex"))
    val selectedExercise: StateFlow<List<String>> = _selectedExercise

    private val _selectedGoals = MutableStateFlow(listOf("goals"))
    val selectedGoals: StateFlow<List<String>> = _selectedGoals

    private val _selectedPace = MutableStateFlow("Beginner 1")
    val selectedPace: StateFlow<String> = _selectedPace

    private val _selectedBreakTime = MutableStateFlow("Beginner 1")
    val selectedBreakTime: StateFlow<String> = _selectedBreakTime

    private val _selectedLevel = MutableStateFlow("Beginner 1")
    val selectedLevel: StateFlow<String> = _selectedLevel

    private val _selectedMusic = MutableStateFlow("relaxing")
    val selectedMusic: StateFlow<String> = _selectedMusic

    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    private val _selectedInstructor = MutableStateFlow("Darlene Robertson")
    val selectedInstructor: StateFlow<String> = _selectedInstructor

    private val _homeUiState = mutableStateOf(UiState())
    val homeUiState: State<UiState> = _homeUiState

    fun event(event: UiEvent) {
        when (event) {
            is UiEvent.SetExercise -> {
                _selectedExercise.update { event.exercise }
            }

            is UiEvent.SetGoals -> {
                _selectedGoals.update { event.goals }
            }

            is UiEvent.SetBreakTime -> {
                _selectedBreakTime.update { event.time }
            }

            is UiEvent.SetLevel -> {
                _selectedLevel.update { event.level }
            }

            is UiEvent.SetPace -> {
                _selectedLevel.update { event.pace }
            }

            is UiEvent.SetInstructor -> {
                _selectedInstructor.update { event.instructor }
            }

            is UiEvent.SetMusic -> {
                _selectedMusic.update { event.music }
            }

            is UiEvent.SetLanguage -> {
                _selectedLanguage.update { event.language }
            }

            is UiEvent.SetTarget -> {
                _homeUiState.value = _homeUiState.value.copy(targetValue = event.target)
            }

            is UiEvent.SetTargetAngle -> {
                _homeUiState.value = _homeUiState.value.copy(targetAngle = event.angle)
            }

            is UiEvent.Start -> {}
            is UiEvent.End -> {}
        }
    }
}