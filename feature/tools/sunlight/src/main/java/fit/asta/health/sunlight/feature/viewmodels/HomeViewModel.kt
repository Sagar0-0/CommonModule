package fit.asta.health.sunlight.feature.viewmodels

import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.Value
import fit.asta.health.common.utils.getCurrentDateTime
import fit.asta.health.common.utils.toUiState
import fit.asta.health.datastore.PrefManager
import fit.asta.health.sunlight.feature.event.SunlightHomeEvent
import fit.asta.health.sunlight.feature.screens.home.homeScreen.SunlightHomeState
import fit.asta.health.sunlight.feature.screens.skin_conditions.util.SkinConditionScreenCode
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import fit.asta.health.sunlight.remote.model.Sup
import fit.asta.health.sunlight.repo.SunlightHomeRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SunlightHomeRepoImpl,
    private val prefManager: PrefManager,
    @UID private val uid: String
) :
    ViewModel() {
    val isStarted = mutableStateOf(false)
    private val totalTime = mutableLongStateOf(0L)
    private val _timerText = mutableStateOf("00:00")
    private val timerText: MutableState<String> = _timerText
    private val _remainingTime = mutableFloatStateOf(0f)
    private val remainingTime: MutableState<Float> = _remainingTime
    private var _countDownTimer: CountDownTimer? = null
    private val totalDConsumed = mutableStateOf("")
    private var achievedIU = 0L
    private var dPerMin = 0L
    var millisOver = 0L
    var totalTimeMillis = 0L
    var millisRemaining = 0L
    val sessionState = mutableStateOf(SunlightSessionData())
    var navigateToCondition: (() -> Unit) = {}
    var navigateToResultScreen: (() -> Unit) = {}

    //init here to avoid late init crash
    private val _homeState: MutableStateFlow<UiState<SunlightHomeData>> =
        MutableStateFlow(UiState.Idle)
    val homeState: StateFlow<UiState<SunlightHomeData>> = _homeState.asStateFlow()

    fun onEvent(event: SunlightHomeEvent) {
        when (event) {
            is SunlightHomeEvent.OnStartTimer -> {
                sessionState.value.startTime = Calendar.getInstance().timeInMillis
                startTimer(event.time)
            }

            is SunlightHomeEvent.OnStopTimer -> {
                stopTimer()
                navigateToResultScreen.invoke()
            }

            is SunlightHomeEvent.OnPause -> {
                onPause()
            }

            is SunlightHomeEvent.OnResume -> {
                onResume()
            }

        }
    }

    private fun startTimer(timeInMillis: Long) {
        viewModelScope.launch {
            _sunlightDataState.emit(
                _sunlightDataState.value.copy(
                    isTimerRunning = mutableStateOf(true),
                )
            )
        }
        _countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                isStarted.value = true
                millisRemaining = millisUntilFinished
                millisOver = totalTimeMillis - millisUntilFinished
                _remainingTime.floatValue = (millisOver.toFloat() / timeInMillis) * 260f
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                viewModelScope.launch {
                    _sunlightDataState.emit(
                        _sunlightDataState.value.copy(
                            isTimerRunning = mutableStateOf(false)
                        )
                    )
                }
                isStarted.value = false
                _remainingTime.floatValue = 1f
                updateTimerText(0)
                sessionState.value.endTime = Calendar.getInstance().timeInMillis
            }
        }
        _countDownTimer?.start()
    }

    private fun onPause() {
        viewModelScope.launch {
            _sunlightDataState.emit(
                _sunlightDataState.value.copy(
                    isTimerRunning = mutableStateOf(true),
                    isTimerPaused = mutableStateOf(true)
                )
            )
        }
        _countDownTimer?.cancel()

    }

    private fun onResume() {
        startTimer(millisRemaining)
        viewModelScope.launch {
            _sunlightDataState.emit(
                _sunlightDataState.value.copy(
                    isTimerPaused = mutableStateOf(false)
                )
            )
        }
    }

    private fun stopTimer() {
        viewModelScope.launch {
            _sunlightDataState.emit(
                _sunlightDataState.value.copy(
                    isTimerRunning = mutableStateOf(false)
                )
            )
        }
        isStarted.value = false
        _remainingTime.floatValue = 0f
        updateTimerText(0)
        _countDownTimer?.cancel()
        sessionState.value.endTime = Calendar.getInstance().timeInMillis
    }

    fun updateTimerText(remainingMillis: Long) {
        val secondsLeft = remainingMillis / 1000
        val minutesLeft = secondsLeft / 60
        totalDConsumed.value =
            "${String.format(" % 02d", achievedIU + ((millisOver / 60000) * dPerMin))} IU"
        _timerText.value =
            "${String.format("%02d", minutesLeft)}:${String.format("%02d", secondsLeft % 60)} min"
        viewModelScope.launch {
            _sunlightDataState.emit(
                _sunlightDataState.value.copy(
                    sunlightProgress = timerText,
                    remainingTime = remainingTime,
                    isTimerRunning = isStarted,
                    totalDConsumed = totalDConsumed
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

    val skinConditionData = mutableStateListOf<Prc>()
    val skinConditionDataMapper = mutableStateMapOf<String, Value?>()
    val supplementData = mutableStateOf<Sup?>(null)


    private val _sunlightDataState: MutableStateFlow<SunlightHomeState> =
        MutableStateFlow(SunlightHomeState())
    val sunlightDataState: StateFlow<SunlightHomeState> = _sunlightDataState.asStateFlow()

    fun getHomeScreenData() {
        _sunlightDataState.value = _sunlightDataState.value.copy(isLoading = true)
        viewModelScope.launch {
            prefManager.address.collectLatest { pref ->
                repository.getSunlightHomeData(
                    uid,
                    pref.lat.toString(),
                    pref.long.toString(),
                    getCurrentDateTime(),
                    pref.currentAddress
                ).onEach { dataState ->
                    _homeState.emit(dataState.toUiState())
                    when (val data = dataState.toUiState()) {
                        UiState.Loading -> {
                            _sunlightDataState.emit(_sunlightDataState.value.copy(isLoading = true))
                        }

                        is UiState.Success -> {
                            skinConditionData.clear()

                            updateDataMapper(data)
                            data.data.let {
                                it.sunLightProgressData?.achIu.let { ach ->
                                    achievedIU = ach ?: 0
                                }
                                it.sunLightProgressData?.rem?.let { time ->
                                    totalTime.longValue = ((time / 60000))
                                    totalTimeMillis = time
                                    updateTimerText(time)
                                }
                                dPerMin = it.sunLightData?.iuPerMin ?: 0
                                _sunlightDataState.emit(
                                    _sunlightDataState.value.copy(
                                        sunlightHomeResponse = it,
                                        isLoading = false,
                                        skinConditionData = skinConditionData,
                                        supplementData = mutableStateOf(it.sunLightData?.sup),
                                        totalTime = totalTime
                                    )
                                )

                                if (it.sunLightData?.prc.isNullOrEmpty()
                                    ||
                                    it.sunLightData?.uid?.equals("000000000000000000000000") == true
                                ) {
                                    skinConditionData.addAll(
                                        listOf(
                                            Prc(
                                                "",
                                                "",
                                                false,
                                                "",
                                                4,
                                                listOf(Value("", "", "", "", ""))
                                            ),
                                            Prc(
                                                "",
                                                "",
                                                false,
                                                "",
                                                4,
                                                listOf(Value("", "", "", "", ""))
                                            ),
                                            Prc(
                                                "",
                                                "",
                                                false,
                                                "",
                                                4,
                                                listOf(Value("", "", "", "", ""))
                                            ),
                                            Prc(
                                                "",
                                                "",
                                                false,
                                                "",
                                                4,
                                                listOf(Value("", "", "", "", ""))
                                            ),
                                        )
                                    )
                                    navigateToCondition.invoke()
                                }

                            }
                        }

                        is UiState.ErrorMessage -> {
                            _sunlightDataState.emit(_sunlightDataState.value.copy(isLoading = false))
                        }

                        else -> {
                            _sunlightDataState.emit(_sunlightDataState.value.copy(isLoading = false))
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

    private fun updateDataMapper(data: UiState.Success<SunlightHomeData>) {
        data.data.let {
            skinConditionData.addAll(it.sunLightData?.prc ?: emptyList())
            supplementData.value = it.sunLightData?.sup
        }
        data.data.sunLightData?.prc?.forEach { prc ->
            skinConditionDataMapper[prc.code] = prc.values.firstOrNull()
        }
    }


    private val _sessionResultDataState: MutableStateFlow<SunlightHomeState> =
        MutableStateFlow(SunlightHomeState())
    val sessionResultDataState: StateFlow<SunlightHomeState> = _sessionResultDataState.asStateFlow()

    fun getSessionResult() {
        _sessionResultDataState.value = _sessionResultDataState.value.copy(isLoading = true)
        repository.getSunlightSessionData(
            SessionDetailBody(
                uid = uid,
                dur = sessionState.value.getDuration(),
                temp = (sunlightDataState.value.sunlightHomeResponse?.sunSlotData?.currTemp
                    ?: 0.0),
                uv = ceil(
                    sunlightDataState.value.sunlightHomeResponse?.sunSlotData?.currUv
                        ?: 0.0
                ).toInt(),
                spf = skinConditionDataMapper[SkinConditionScreenCode.SUNSCREEN_SPF_SCREEN]?.code
                    ?: "",
                start = sessionState.value.startTime.toString(),
                end = sessionState.value.endTime.toString(),
                exp = skinConditionDataMapper[SkinConditionScreenCode.EXPOSURE_SCREEN]?.code?.toIntOrNull()
                    ?: 0
            )
        ).onEach { dataState ->
            when (val data = dataState.toUiState()) {
                UiState.Loading -> {
                    _sessionResultDataState.emit(_sessionResultDataState.value.copy(isLoading = true))
                }

                is UiState.Success -> {
                    skinConditionData.clear()
                    data.data.let {
                        _sessionResultDataState.emit(
                            _sessionResultDataState.value.copy(
                                sunlightSessionData = it,
                                isLoading = false
                            )
                        )
                    }
                }

                is UiState.ErrorMessage -> {
                    _sessionResultDataState.emit(_sessionResultDataState.value.copy(isLoading = false))
                }

                else -> {
                    _sessionResultDataState.emit(_sessionResultDataState.value.copy(isLoading = false))
                }
            }
        }.launchIn(viewModelScope)
    }


    private val _helpAndSuggestionState: MutableStateFlow<UiState<HelpAndNutrition>> =
        MutableStateFlow(UiState.Idle)
    val helpAndSuggestionState: StateFlow<UiState<HelpAndNutrition>> =
        _helpAndSuggestionState.asStateFlow()

    fun getSupplementAndFoodInfo() {
        _helpAndSuggestionState.value = UiState.Loading
        repository.getSupplementAndFoodInfo().onEach { dataState ->
            _helpAndSuggestionState.emit(dataState.toUiState())
        }.launchIn(viewModelScope)
    }


}