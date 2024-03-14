package fit.asta.health.feature.walking.vm

import android.os.RemoteException
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarttoolfactory.colorpicker.util.roundToTwoDigits
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.common.health_data.ExerciseSessionData
import fit.asta.health.common.health_data.HealthConnectManager
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Prc
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.data.walking.local.model.Day
import fit.asta.health.data.walking.local.model.Settings
import fit.asta.health.data.walking.remote.model.Distance
import fit.asta.health.data.walking.remote.model.Duration
import fit.asta.health.data.walking.remote.model.PutData
import fit.asta.health.data.walking.remote.model.Steps
import fit.asta.health.data.walking.remote.model.Target
import fit.asta.health.data.walking.repo.WalkingToolRepo
import fit.asta.health.data.walking.usecase.DayUseCases
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
import java.io.IOException
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WalkingViewModel
@Inject constructor(
    private val dayUseCases: DayUseCases,
    private val repo: WalkingToolRepo,
    private val prefManager: PrefManager,
    @UID private val uid: String,
    private val healthConnectManager: HealthConnectManager
) : ViewModel() {

    private val _sessionList = MutableStateFlow<List<Day>>(listOf())
    val sessionList = _sessionList.asStateFlow()

    val availability = healthConnectManager.availability
    private var getTodayProgressJob: Job? = null

    private val _stepsHomeDataState = MutableStateFlow<UiState<StepsUiState>>(UiState.Idle)
    val stepsHomeDataState = _stepsHomeDataState.asStateFlow()
    private var target = mutableStateOf(TargetData())

    private val _selectedData = MutableStateFlow<List<Prc>>(listOf())
    val selectedData = _selectedData.asStateFlow()

    private val _sheetDataList = MutableStateFlow<List<NetSheetData>>(listOf())
    val sheetDataList = _sheetDataList.asStateFlow()

    val firstTimeOpen = mutableStateOf(true)
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

    val permissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(DistanceRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
    )
    var permissionsGranted = mutableStateOf(false)
        private set

    var sessionMetrics: MutableState<ExerciseSessionData> = mutableStateOf(ExerciseSessionData())
        private set

    var healthUiState: HealthUiState by mutableStateOf(HealthUiState.Uninitialized)
        private set

    val permissionsLauncher = healthConnectManager.requestPermissionsActivityContract()

    fun initialLoad() {
        readAssociatedSessionData()
    }

    private fun readAssociatedSessionData() {
        viewModelScope.launch {
            tryWithPermissionsCheck {
                healthConnectManager.readAssociatedSessionData().collect {
                    sessionMetrics.value = it
                }
            }
        }
    }

    fun checkAvailability() {
        healthConnectManager.checkAvailability()
    }

    /**
     * Provides permission check and error handling for Health Connect suspend function calls.
     *
     * Permissions are checked prior to execution of [block], and if all permissions aren't granted
     * the [block] won't be executed, and [permissionsGranted] will be set to false, which will
     * result in the UI showing the permissions button.
     *
     * Where an error is caught, of the type Health Connect is known to throw, [HealthUiState] is set to
     * [HealthUiState.Error], which results in the snackbar being used to show the error message.
     */
    private suspend fun tryWithPermissionsCheck(block: suspend () -> Unit) {
        permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
        healthUiState = try {
            if (permissionsGranted.value) {
                Log.d("rishi", "permission granted for health connect")
                block()
            } else {
                Log.d("rishi", "permission not granted for health connect")
            }
            HealthUiState.Done
        } catch (remoteException: RemoteException) {
            HealthUiState.Error(remoteException)
        } catch (securityException: SecurityException) {
            HealthUiState.Error(securityException)
        } catch (ioException: IOException) {
            HealthUiState.Error(ioException)
        } catch (illegalStateException: IllegalStateException) {
            // HealthUiState.Done
            HealthUiState.Error(illegalStateException)
        }
    }

    sealed class HealthUiState {
        data object Uninitialized : HealthUiState()
        data object Done : HealthUiState()

        // A random UUID is used in each Error object to allow errors to be uniquely identified,
        // and recomposition won't result in multiple snackbars.
        data class Error(val exception: Throwable, val uuid: UUID = UUID.randomUUID()) :
            HealthUiState()
    }

    fun setStepsPermissionRejectedCount(count: Int) {
        viewModelScope.launch { repo.updateStepsPermissionRejectedCount(count) }
    }

    private fun startProgressHome() {
        _stepsHomeDataState.value = UiState.Loading
        viewModelScope.launch {
            val result = repo.getHomeData(uid)
            _stepsHomeDataState.value = when (result) {
                is ResponseState.Success -> {
                    _selectedData.value = result.data.walkingTool.prc
                    target.value = TargetData(
                        targetDistance = result.data.walkingRecommendation.recommend.distance.distance,
                        targetDuration = result.data.walkingRecommendation.recommend.duration.duration
                    )
                    val stepsUiState = StepsUiState(
                        stepCount = 0,
                        caloriesBurned = 0,
                        distance = 0f,
                        recommendDistance = result.data.walkingRecommendation.recommend.distance.distance,
                        recommendDuration = result.data.walkingRecommendation.recommend.duration.duration
                    )
                    UiState.Success(stepsUiState)
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

    fun setTarget(distance: Float, duration: Float) {
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
                _sessionList.value = dayList
            }
        }.launchIn(viewModelScope)
    }


    fun getSheetItemValue(code: String) {
        viewModelScope.launch {
            when (code) {
                "goal" -> {
                    when (val result = repo.getSheetGoalsData("walk")) {
                        is ResponseState.Success -> {
                            _sheetDataList.value = (result.data)
                        }

                        else -> {}
                    }
                }

                "mode" -> {
                    _sheetDataList.value = (
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
                            _sheetDataList.value = (result.data)
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    fun setMultiple(index: Int, itemIndex: Int) {
        val data = _selectedData.value[index]
        val item = _sheetDataList.value[itemIndex]
        _sheetDataList.value = _sheetDataList.value.toMutableList().apply {
            this[itemIndex] = this[itemIndex].copy(isSelected = !item.isSelected)
        }
        data.values = _sheetDataList.value.filter { it.isSelected }.map { it.toValue() }.toList()
        _selectedData.value = _selectedData.value.toMutableList().apply {
            this[index] = data
        }
    }

    fun setSingle(index: Int, itemIndex: Int) {
        val data = _selectedData.value[index]
        _sheetDataList.value.map { it.isSelected = false }
        val item = _sheetDataList.value[itemIndex].copy(isSelected = true)
        _sheetDataList.value = _sheetDataList.value.toMutableList().apply {
            this[itemIndex] = item
        }
        data.values = listOf(item.toValue())
        _selectedData.value = _selectedData.value.toMutableList().apply {
            this[index] = data
        }
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
                    uid = uid,
                    wea = true,
                    tgt = Target(
                        distance = Distance(
                            distance = target.value.targetDistance.roundToTwoDigits(),
                            unit = "km"
                        ),
                        duration = Duration(
                            duration = target.value.targetDuration.toFloat().roundToTwoDigits(),
                            unit = "mins"
                        ),
                        steps = Steps(
                            steps = target.value.targetDistance.times(100_000).div(72).toInt(),
                            unit = "steps"
                        )
                    ),
                    prc = _selectedData.value
                )
            )
        }
    }

}
