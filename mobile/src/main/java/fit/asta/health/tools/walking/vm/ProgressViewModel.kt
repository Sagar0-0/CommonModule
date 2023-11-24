package fit.asta.health.tools.walking.vm

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.network.utils.toValue
import fit.asta.health.tools.walking.core.data.Settings
import fit.asta.health.tools.walking.core.data.source.network.request.Distance
import fit.asta.health.tools.walking.core.data.source.network.request.Duration
import fit.asta.health.tools.walking.core.data.source.network.request.PutData
import fit.asta.health.tools.walking.core.data.source.network.request.Steps
import fit.asta.health.tools.walking.core.data.source.network.request.Target
import fit.asta.health.tools.walking.core.domain.model.Day
import fit.asta.health.tools.walking.core.domain.repository.WalkingToolRepo
import fit.asta.health.tools.walking.core.domain.usecase.DayUseCases
import fit.asta.health.tools.walking.service.FitManager
import fit.asta.health.tools.walking.view.home.StepsUiState
import fit.asta.health.tools.walking.view.home.TargetData
import fit.asta.health.tools.walking.view.session.ProgressState
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
    private val dayUseCases: DayUseCases,
    private val repo: WalkingToolRepo
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

    private val _sessionList = mutableStateListOf<Day>()
    val sessionList = MutableStateFlow(_sessionList)

    private var getProgressJob: Job? = null
    private var getTodayProgressJob: Job? = null

    private val _mutableState = MutableStateFlow<UiState<StepsUiState>>(UiState.Idle)
    val state = _mutableState.asStateFlow()
    private var target = mutableStateOf(TargetData())

    private val _selectedData = mutableStateListOf<Prc>()
    val selectedData = MutableStateFlow(_selectedData)
    private val _sheetDataList = mutableStateListOf<NetSheetData>()
    val sheetDataList = MutableStateFlow(_sheetDataList)

    init {
        getTodayProgressList(LocalDate.now())
    }

    fun startProgressHome(context: Context) {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            FitManager.getDailyFitnessData(context).collect { dailyFit ->
                val result = repo.getHomeData("6309a9379af54f142c65fbfe")
                _mutableState.value = when (result) {
                    is ResponseState.Success -> {
                        _selectedData.clear()
                        _selectedData.addAll(result.data.walkingTool.prc)
                        target.value = TargetData(
                            targetDistance = result.data.walkingRecommendation.recommend.distance.distance,
                            targetDuration = result.data.walkingRecommendation.recommend.duration.duration
                        )
                        UiState.Success(
                            StepsUiState(
                                stepCount = dailyFit.stepCount,
                                caloriesBurned = dailyFit.caloriesBurned,
                                distance = dailyFit.distance,
                                recommendDistance = result.data.walkingRecommendation.recommend.distance.distance,
                                recommendDuration = result.data.walkingRecommendation.recommend.duration.duration
                            )
                        )
                    }

                    is ResponseState.ErrorMessage -> {
                        UiState.ErrorMessage(result.resId)
                    }

                    is ResponseState.ErrorRetry -> {
                        UiState.ErrorRetry(result.resId)
                    }

                    else -> {
                        UiState.NoInternet
                    }
                }
            }
        }
    }


    fun startSession() {
        viewModelScope.launch {
            dayUseCases.setDay(
                setting = Settings(),
                targetDuration = target.value.targetDuration,
                targetDistance = target.value.targetDistance
            )
        }
        viewModelScope.launch {
            delay(1000)
            getProgress(LocalDate.now())
        }
    }

    fun setTarget(distance: Float, duration: Int) {
        if (distance > 0) {
            target.value = target.value.copy(targetDistance = distance)
        }
        if (duration > 0) {
            target.value = target.value.copy(targetDuration = duration)
        }
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

    private fun getTodayProgressList(date: LocalDate) {
        getTodayProgressJob?.cancel()
        getTodayProgressJob = dayUseCases.getAllDayData(date).onEach { dayList ->
            if (dayList.isNotEmpty()) {
                _sessionList.clear()
                _sessionList.addAll(dayList)
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

    fun getSheetItemValue(code: String) {
//        viewModelScope.launch {
//            when (val result = meditationRepo.getSheetData(code)) {
//                is ResponseState.Success -> {
//                    _sheetDataList.clear()
//                    _sheetDataList.addAll(result.data)
//                }
//
//                else -> {}
//            }
//        }
    }

    fun setMultiple(index: Int, itemIndex: Int) {
        val data = _selectedData[index]
        val item = _sheetDataList[itemIndex]
        _sheetDataList[itemIndex] = item.copy(isSelected = !item.isSelected)
        data.values = _sheetDataList.filter { it.isSelected }.map { it.toValue() }.toList()
        _selectedData[index] = data
    }

    fun setSingle(index: Int, itemIndex: Int) {
        val data = _selectedData[index]
        _sheetDataList.map { it.isSelected = false }
        val item = _sheetDataList[itemIndex].copy(isSelected = true)
        _sheetDataList[itemIndex] = item
        data.values = listOf(item.toValue())
        _selectedData[index] = data
    }

    private fun putData() {
        viewModelScope.launch {
            repo.putData(
                PutData(
                    code = 3,
                    id = "",
                    name = "walking",
                    sType = 1,
                    uid = "",
                    wea = true,
                    tgt = Target(
                        dis = Distance(
                            dis = 0f,
                            unit = "km"
                        ),
                        dur = Duration(
                            dur = 0f,
                            unit = "mins"
                        ),
                        steps = Steps(
                            steps = 0,
                            unit = "steps"
                        )
                    ),
                    prc = _selectedData.toList()

                )
            )
        }
    }

}
