package fit.asta.health.data.walking.service

import fit.asta.health.data.walking.usecase.DayUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

class StepController(
    private val dayUseCases: DayUseCases,
    currentDateFlow: StateFlow<LocalDate>,
) {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val _stats = MutableStateFlow(StepState(LocalDate.now(), 0, 0.0, 0, 0))
    val stats: StateFlow<StepState> = _stats.asStateFlow()

    private var getStatsJob: Job? = null
    var date: LocalDate = LocalDate.now()

    init {
        coroutineScope.launch {
            currentDateFlow.collect {
                date = it
                getStats(it)
            }
        }
    }

    private fun getStats(date: LocalDate) {
        getStatsJob?.cancel()

        getStatsJob = dayUseCases.getDailyData(date).onEach { day ->
            if (day == null) {
                dayUseCases.incrementStepsInDaily(date, 0)
            } else {
                _stats.value = _stats.value.copy(
                    date = date,
                    steps = day.steps,
                    distanceTravelled = day.distanceTravelled,
                    calorieBurned = day.calorieBurned.roundToInt(),
                )
            }
        }.launchIn(coroutineScope)
    }

    private val rawStepSensorReadings = MutableStateFlow(StepCounterEvent(0, LocalDate.MIN))
    private var previousStepCount: Int? = null


    init {
        rawStepSensorReadings.drop(1).onEach { event ->
            val stepCountDifference = event.stepCount - (previousStepCount ?: event.stepCount)
            previousStepCount = event.stepCount
            dayUseCases.incrementStepsInDaily(date, stepCountDifference)
        }.launchIn(coroutineScope)
    }

    fun onStepCountChanged(newStepCount: Int, eventDate: LocalDate) {
        rawStepSensorReadings.value = StepCounterEvent(newStepCount, eventDate)
    }
}
