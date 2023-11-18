package fit.asta.health.tools.walking.progress

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.tools.walking.core.data.Settings
import fit.asta.health.tools.walking.core.domain.model.toDailyFitness
import fit.asta.health.tools.walking.core.domain.usecase.DayUseCases
import fit.asta.health.tools.walking.nav.DailyFitnessModel
import fit.asta.health.tools.walking.nav.FitManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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
class ProgressViewModel @Inject constructor(
    private val dayUseCases: DayUseCases
) : ViewModel() {

    private val _progress = MutableStateFlow(
        ProgressState(
            date = LocalDate.MIN,
            stepsTaken = 0,
            dailyGoal = 0,
            calorieBurned = 0,
            duration = 0,
            distanceTravelled = 0.0,
            carbonDioxideSaved = 0.0,
            state = true
        )
    )
    val progress: StateFlow<ProgressState> = _progress.asStateFlow()
    private val _progressHome = MutableStateFlow(
        DailyFitnessModel(0, 0, 0f)
    )
    val progressHome: StateFlow<DailyFitnessModel> = _progressHome.asStateFlow()

    private val _sheetDataList = mutableStateListOf<DailyFitnessModel>()
    val sheetDataList = MutableStateFlow(_sheetDataList)

    private var getProgressJob: Job? = null
    private var getTodayProgressJob: Job? = null
    private var getHomeProgressJob: Job? = null

    init {
        getTodayProgressList(LocalDate.now())
    }

    fun startProgressHome(context: Context) {
        viewModelScope.launch {
            FitManager.getDailyFitnessData(context).collect {
                _progressHome.value = it
            }
        }
        getTodayProgressList(LocalDate.now())
    }

    fun startSession() {
        viewModelScope.launch {
            dayUseCases.setDay(Settings())
        }
        viewModelScope.launch {
            delay(1000)
            getProgress(LocalDate.now())
        }
    }

    private fun getProgress(date: LocalDate) {
        getProgressJob?.cancel()

        getProgressJob = dayUseCases.getDay(date).onEach { day ->
            _progress.value = progress.value.copy(
                date = day.date,
                stepsTaken = day.steps,
                dailyGoal = day.goal,
                calorieBurned = day.calorieBurned.roundToInt(),
                distanceTravelled = day.distanceTravelled,
                carbonDioxideSaved = day.carbonDioxideSaved,
                duration = day.duration,
                state = day.state
            )
        }.launchIn(viewModelScope)
    }

    private fun getTodayProgressList(date: LocalDate) {
        getTodayProgressJob?.cancel()
        getTodayProgressJob = dayUseCases.getAllDayData(date).onEach { dayList ->
            if (dayList.isNotEmpty()) {
                _sheetDataList.clear()
                _sheetDataList.addAll(dayList.map { it.toDailyFitness() })
            }
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
