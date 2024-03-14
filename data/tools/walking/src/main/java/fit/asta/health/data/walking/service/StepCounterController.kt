package fit.asta.health.data.walking.service

import fit.asta.health.data.walking.usecase.DayUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

class StepCounterController(
    private val dayUseCases: DayUseCases,
    private val coroutineScope: CoroutineScope,
) {

    private val _stats = MutableStateFlow(StepCounterState(LocalDate.now(), 0, 0f, 0f, 0.0, 0, 0))
    val stats: StateFlow<StepCounterState> = _stats.asStateFlow()

    private var getStatsJob: Job? = null

    init {
        coroutineScope.launch {
            getStats(LocalDate.now())
            while (currentCoroutineContext().isActive) {
                dayUseCases.incrementStepDuration(LocalDate.now(), 1)
                delay(60000)
            }
        }
    }

    private fun getStats(date: LocalDate) {
        getStatsJob?.cancel()

        getStatsJob = dayUseCases.getDay(date).onEach { day ->
            _stats.value = _stats.value.copy(
                date = date,
                steps = day.steps,
                goalDistance = day.targetDistance,
                goalDuration = day.targetDuration,
                distanceTravelled = day.distanceTravelled,
                calorieBurned = day.calorieBurned.roundToInt(),
                duration = day.duration
            )
        }.launchIn(coroutineScope)
    }

    private val rawStepSensorReadings = MutableStateFlow(StepCounterEvent(0, LocalDate.MIN))
    private var previousStepCount: Int? = null


    init {
        rawStepSensorReadings.drop(1).onEach { event ->
            val stepCountDifference = event.stepCount - (previousStepCount ?: event.stepCount)
            previousStepCount = event.stepCount
            dayUseCases.incrementStepCount(event.eventDate, stepCountDifference)
        }.launchIn(coroutineScope)
    }

    fun onStepCountChanged(newStepCount: Int, eventDate: LocalDate) {
        rawStepSensorReadings.value = StepCounterEvent(newStepCount, eventDate)
    }
}
