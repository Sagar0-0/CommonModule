package fit.asta.health.feature.walking.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.data.walking.domain.usecase.DayUseCases
import fit.asta.health.feature.walking.view.session.ProgressState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class StepsSessionViewModel @Inject constructor(
    private val dayUseCases: DayUseCases
) : ViewModel() {
    private val _progress = MutableStateFlow(
        ProgressState(
            date = LocalDate.MIN,
            stepsTaken = 0,
            targetDistance = 0f,
            targetDuration = 0,
            calorieBurned = 0,
            duration = 0,
            distanceTravelled = 0.0,
            carbonDioxideSaved = 0.0,
            state = true
        )
    )
    val progress: StateFlow<ProgressState> = _progress.asStateFlow()
    private var getProgressJob: Job? = null

    init {
        getProgress(LocalDate.now())
    }

    private fun getProgress(date: LocalDate) {
        getProgressJob?.cancel()

        getProgressJob = dayUseCases.getDay(date).onEach { day ->
            _progress.value = progress.value.copy(
                date = day.date,
                stepsTaken = day.steps,
                targetDuration = day.targetDuration,
                targetDistance = day.targetDistance,
                calorieBurned = day.calorieBurned.roundToInt(),
                distanceTravelled = day.distanceTravelled,
                carbonDioxideSaved = day.carbonDioxideSaved,
                duration = day.duration,
                state = day.state
            )
        }.launchIn(viewModelScope)
    }

    fun pause() {
        _progress.value = progress.value.copy(state = false)
        viewModelScope.launch { dayUseCases.changeStepsState(LocalDate.now(), false) }
    }

    fun resume() {
        _progress.value = progress.value.copy(state = true)
        viewModelScope.launch { dayUseCases.changeStepsState(LocalDate.now(), true) }
    }

    fun stop() {
        viewModelScope.launch { dayUseCases.changeStepsState(LocalDate.now(), false) }
    }
}