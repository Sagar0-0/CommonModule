package fit.asta.health.feature.walking.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarttoolfactory.colorpicker.util.roundToTwoDigits
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.walking.data.Settings
import fit.asta.health.data.walking.data.source.network.request.Distance
import fit.asta.health.data.walking.data.source.network.request.Duration
import fit.asta.health.data.walking.data.source.network.request.PutData
import fit.asta.health.data.walking.data.source.network.request.Steps
import fit.asta.health.data.walking.data.source.network.request.Target
import fit.asta.health.data.walking.domain.model.Day
import fit.asta.health.data.walking.domain.repository.WalkingToolRepo
import fit.asta.health.data.walking.domain.usecase.DayUseCases
import fit.asta.health.data.walking.service.FitManager
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.walking.view.home.StepsUiState
import fit.asta.health.feature.walking.view.home.TargetData
import fit.asta.health.network.utils.toValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WalkingViewModel @Inject constructor(
    private val dayUseCases: DayUseCases,
    private val repo: WalkingToolRepo,
    private val fitManager: FitManager,
    private val prefManager: PrefManager,
) : ViewModel() {


    private val _sessionList = mutableStateListOf<Day>()
    val sessionList = MutableStateFlow(_sessionList)


    private var getTodayProgressJob: Job? = null

    private val _mutableState = MutableStateFlow<UiState<StepsUiState>>(UiState.Idle)
    val state = _mutableState.asStateFlow()
    private var target = mutableStateOf(TargetData())

    private val _selectedData = mutableStateListOf<Prc>()
    val selectedData = MutableStateFlow(_selectedData)
    private val _sheetDataList = mutableStateListOf<NetSheetData>()
    val sheetDataList = MutableStateFlow(_sheetDataList)

    val stepsPermissionRejectedCount = repo.userPreferences
        .map {
            it.stepsPermissionRejectedCount
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0,
        )

    init {
        startProgressHome()
        getTodayProgressList(LocalDate.now())
    }


    fun setStepsPermissionRejectedCount(count: Int) {
        viewModelScope.launch { repo.updateStepsPermissionRejectedCount(count) }
    }

    private fun startProgressHome() {
        _mutableState.value = UiState.Loading
        viewModelScope.launch {
            fitManager.getDailyFitnessData().collect { dailyFit ->
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
            prefManager.setSessionStatus(true)
            putData()
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


    private fun getTodayProgressList(date: LocalDate) {
        getTodayProgressJob?.cancel()
        getTodayProgressJob = dayUseCases.getAllDayData(date).onEach { dayList ->
            if (dayList.isNotEmpty()) {
                _sessionList.clear()
                _sessionList.addAll(dayList)
            }
        }.launchIn(viewModelScope)
    }


    fun getSheetItemValue(code: String) {
        _sheetDataList.clear()
        viewModelScope.launch {
            when (code) {
                "goal" -> {
                    when (val result = repo.getSheetGoalsData("walk")) {
                        is ResponseState.Success -> {
                            _sheetDataList.addAll(result.data)
                        }

                        else -> {}
                    }
                }

                "mode" -> {
                    _sheetDataList.addAll(
                        listOf(
                            NetSheetData(
                                id = "",
                                code = "indoor",
                                name = "Indoor",
                                dsc = "",
                                since = 1,
                                type = 1,
                                url = "",
                                isSelected = false
                            ),
                            NetSheetData(
                                id = "",
                                code = "outdoor",
                                name = "Outdoor",
                                dsc = "",
                                since = 1,
                                type = 1,
                                url = "",
                                isSelected = false
                            )
                        )
                    )
                }

                else -> {
                    when (val result = repo.getSheetData(code)) {
                        is ResponseState.Success -> {
                            _sheetDataList.addAll(result.data)
                        }

                        else -> {}
                    }
                }
            }
        }
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
                    code = "walking",
                    id = "",
                    name = "walking",
                    sType = 1,
                    type = 6,
                    uid = "6309a9379af54f142c65fbfe",
                    wea = true,
                    tgt = Target(
                        dis = Distance(
                            dis = target.value.targetDistance.roundToTwoDigits(),
                            unit = "km"
                        ),
                        dur = Duration(
                            dur = target.value.targetDuration.toFloat().roundToTwoDigits(),
                            unit = "mins"
                        ),
                        steps = Steps(
                            steps = target.value.targetDistance.times(100_000).div(72).toInt(),
                            unit = "steps"
                        )
                    ),
                    prc = _selectedData.toList()
                )
            )
        }
    }

    fun checkPermission() = fitManager.checkFitPermission()
    fun requestPermission() {
        fitManager.requestPermissions()
    }
}
