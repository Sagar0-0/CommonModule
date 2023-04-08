package fit.asta.health.tools.walking.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.scheduler.compose.SchedulerActivity
import fit.asta.health.tools.walking.db.StepsData
import fit.asta.health.tools.walking.model.LocalRepo
import fit.asta.health.tools.walking.model.WalkingToolRepo
import fit.asta.health.tools.walking.model.domain.WalkingTool
import fit.asta.health.tools.walking.model.network.request.*
import fit.asta.health.tools.walking.model.network.request.Target
import fit.asta.health.tools.walking.view.home.HomeUIState
import fit.asta.health.tools.walking.view.home.StepCounterUIEvent
import fit.asta.health.tools.walking.view.steps_counter.StepCounterUIState
import fit.asta.health.tools.walking.work.CountStepsService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@ExperimentalCoroutinesApi
@HiltViewModel
class WalkingViewModel
@Inject constructor(
    private val walkingToolRepo: WalkingToolRepo,
    private val localRepo: LocalRepo
) : ViewModel() {
    val date = LocalDate.now().dayOfMonth
    val uid = "6309a9379af54f142c65fbfe"
    val id = "6417eeca8231fd62c072f3d9"
    private var serviceIntent: Intent? = null
    private val _homeUiState = mutableStateOf(HomeUIState())
    val homeUiState: State<HomeUIState> = _homeUiState

    private val _apiState = mutableStateOf(WalkingTool())
    val ApiState: State<WalkingTool> = _apiState

    val startWorking = mutableStateOf(false)

    init {
        setupDatabaseAndUpdateUi()
        loadWalkingToolData()
        getYesterdayData()
        updateServerIn24Hr()
    }

    private fun setupDatabaseAndUpdateUi() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(LocalDate.now().dayOfMonth)
            if (data == null) {
                val stepsData = StepsData(
                    date = LocalDate.now().dayOfMonth, status = "", initialSteps = 0,
                    allSteps = 0, time = 0, realtime = 0, distanceRecommend = 0.0,
                    durationRecommend = 0, distanceTarget = 0.0, durationTarget = 0,
                    id = id, calories = 0.0, weightLoosed = 0.0, appliedAngleDistance = 0f,
                    appliedAngleDuration = 0f
                )
                localRepo.insert(stepsData)
            } else {
                if (data.status == "active") {
                    startWorking.value = true
                    _homeUiState.value = _homeUiState.value.copy(
                        durationProgress = ((System.nanoTime() - data.time) / 1_000_000_000L / 60L).toInt(),
                        start = true
                    )
                }
                _homeUiState.value = _homeUiState.value.copy(
                    distance = (data.allSteps.toDouble() / 1408.0),
                    steps = data.allSteps,
                    duration = data.realtime,
                    appliedAngleDuration = data.appliedAngleDuration,
                    appliedAngleDistance = data.appliedAngleDistance,
                )
            }
        }
    }

    private fun loadWalkingToolData() {
        viewModelScope.launch {
            walkingToolRepo.getHomeData(userId = uid)
                .collect {
                    when (it) {
                        is NetworkResult.Loading -> {}
                        is NetworkResult.Success -> {
                            _apiState.value = it.data!!
                            val date = localRepo.getStepsData(LocalDate.now().dayOfMonth)
                            if (date != null) {
                                localRepo.updateTargetAndRecommend(
                                    date = date.date,
                                    distanceRecommend = it.data.distanceRecommend,
                                    durationRecommend = it.data.durationRecommend,
                                    distanceTarget = it.data.distanceTarget,
                                    durationTarget = it.data.durationTarget
                                )
                            }
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

    fun onGoalSelected(goal: List<String>) {
        _selectedGoal.value = goal
    }

    fun onWalkTypesSelected(walkTypes: String) {
        _selectedWalkTypes.value = walkTypes
    }


    fun onUIEvent(uiEvent: StepCounterUIEvent) {
        when (uiEvent) {
            is StepCounterUIEvent.StartButtonClicked -> {
                changeStatus("active")
                changeTime()
                startWorking.value = true
                _homeUiState.value = _homeUiState.value.copy(start = true)
                checkPutDataIsRecommend()
            }
            is StepCounterUIEvent.StopButtonClicked -> {
                endScreen()
            }
            is StepCounterUIEvent.EndButtonClicked -> {
                startWorking.value = false
                _homeUiState.value = _homeUiState.value.copy(start = false)
                changeStatus("inactive")
                changeRealTime()
                setupDatabaseAndUpdateUi()
            }
            is StepCounterUIEvent.ChangeTargetDuration -> {
                setTargetDuration(uiEvent.input)
            }
            is StepCounterUIEvent.ChangeTargetDistance -> {
                setTargetDistance(uiEvent.input)
            }
            is StepCounterUIEvent.ChangeAngelDuration -> {
                setAngleDuration(uiEvent.input)
            }
            is StepCounterUIEvent.ChangeAngleDistance -> {
                setAngleDistance(uiEvent.input)
            }

            else -> {}
        }
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


    private fun putDataToServer() {
        viewModelScope.launch {
            val id = localRepo.getStepsData(date)
            val result = walkingToolRepo.putData(setData(id = id?.id ?: "", uid = uid))
            when (result) {
                is NetworkResult.Success -> {
                    localRepo.updateIdForPutData(date, id = result.data!!.data.id)
                    loadWalkingToolData()
                }
                is NetworkResult.Error -> {
                    Log.d("angle", "putDataToServer: ${result.message}")
                }
                else -> {}
            }
        }
    }

    private val _uiStateStep = mutableStateOf(StepCounterUIState())
    val uiStateStep: State<StepCounterUIState> = _uiStateStep
    private fun endScreen() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(date)
            if (data != null && data.date == date) {
                val steps = data.allSteps
                val stepLength = 0.7f
                val timeNs = (System.nanoTime() - data.time)
                val speed = calculateSpeed(steps, stepLength, timeNs)
                val distance = (data.allSteps / 1408)
                val calories = calculateCaloriesBurned(stepsCount = steps)
                val weight = calculateWeightLoss(calories)
                localRepo.updateCaloriesAndWeightLoosed(date, calories, weight)
                _uiStateStep.value = _uiStateStep.value.copy(
                    calories = calories, steps = steps,
                    distance = distance, speed = speed,
                    time = (timeNs / 1_000_000_000L / 60L),
                    weightLoosed = weight
                )
            }
        }
    }

    private fun changeStatus(status: String) {
        viewModelScope.launch {
            localRepo.updateStatus(date, status)
        }
    }

    private fun changeTime() {
        viewModelScope.launch {
            localRepo.updateTime(date, System.nanoTime())
        }
    }

    private fun changeRealTime() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(date)
            if (data != null) {
                val time =
                    ((System.nanoTime() - data.time) / 1_000_000_000L / 60L).toInt() + data.realtime
                localRepo.updateRealTime(date, time)
            }
        }
    }

    fun startSchedulerActivity(context: Context)  {
        SchedulerActivity.launch(context =context)
    }

    fun startService(context: Context) {
        if (serviceIntent == null) {
            serviceIntent = Intent(context, CountStepsService::class.java)
            ContextCompat.startForegroundService(context, serviceIntent!!)
        }
    }

    fun stopService(context: Context) {
        serviceIntent = Intent(context, CountStepsService::class.java)
        context.stopService(serviceIntent!!)
    }

    fun changeUi(data: HomeUIState) {
        if (startWorking.value) {
            _homeUiState.value = _homeUiState.value.copy(
                distance = data.distance,
                steps = data.steps,
                duration = data.duration,
                durationProgress = data.durationProgress,
                valueDistanceRecommendation = data.valueDistanceRecommendation,
                valueDurationRecommendation = data.valueDurationRecommendation,
                valueDistanceGoal = data.valueDistanceGoal,
                valueDurationGoal = data.valueDurationGoal,
                appliedAngleDuration = data.appliedAngleDuration,
                appliedAngleDistance = data.appliedAngleDistance
            )
        }
    }

    fun setData(id: String, uid: String): PutData {
        Log.d("angle", "setdata: ${_homeUiState.value}")
        return PutData(
            code = 3,
            id = id,
            name = "walking",
            sType = 1,
            uid = uid,
            wea = true,
            tgt = Target(
                dis = Distance(
                    dis = _homeUiState.value.distanceTarget,
                    unit = "km"
                ),
                dur = Duration(
                    dur = _homeUiState.value.durationTarget.toInt(),
                    unit = "mins"
                ),
                steps = Steps(
                    steps = (_homeUiState.value.distanceTarget * 1408f).toInt(),
                    unit = "steps"
                )
            ),
            prc = listOf(
                Prc(
                    id = "",
                    ttl = "mode",
                    dsc = "mode",
                    values = listOf(
                        Value(
                            id = "",
                            name = "indoor",
                            value = "indoor"
                        )
                    )
                ),
                Prc(
                    id = "",
                    ttl = "music",
                    code = "music_tool",
                    dsc = "music",
                    values = listOf(
                        Value(
                            id = "",
                            name = "spotify",
                            value = "spotify"
                        )
                    )
                ),
                Prc(
                    id = "",
                    ttl = "goal",
                    dsc = "goal",
                    values = _selectedGoal.value.map {
                        Value(id = "", name = it, value = it)
                    }
                ),
                Prc(
                    id = "",
                    ttl = "type",
                    dsc = "type",
                    values = listOf(
                        Value(
                            id = "",
                            name = _selectedWalkTypes.value,
                            value = _selectedWalkTypes.value
                        )
                    )
                )
            )
        )

    }


    private fun calculateWeightLoss(caloriesBurned: Double): Double {
        return caloriesBurned / 7.7 // 7700 calories per kilogram of body fat
    }

    private fun calculateCaloriesBurned(
        bodyWeight: Double = 50.0,
        distancePerStep: Double = 0.7,
        stepsCount: Int
    ): Double {
        val metersPerStep = distancePerStep * 0.3048 // convert feet to meters
        val caloriesPerKilogramPerMeter = 0.57 // average value for walking/running
        return bodyWeight * stepsCount * metersPerStep * caloriesPerKilogramPerMeter
    }

    private fun setTargetDuration(durationTarget: Float) {
        _homeUiState.value = _homeUiState.value.copy(durationTarget = durationTarget)
    }

    private fun setTargetDistance(distanceTarget: Float) {
        _homeUiState.value = _homeUiState.value.copy(distanceTarget = distanceTarget)
    }

    private fun setAngleDistance(input: Float) {
        _homeUiState.value = _homeUiState.value.copy(appliedAngleDistance = input)
    }

    private fun setAngleDuration(input: Float) {
        _homeUiState.value = _homeUiState.value.copy(appliedAngleDuration = input)
    }

    private fun checkPutDataIsRecommend() {
        viewModelScope.launch {
            val targetDistance = _homeUiState.value.distanceTarget
            val targetDuration = _homeUiState.value.durationTarget
            val distanceTarget = _apiState.value.distanceRecommend.toFloat()
            val durationTarget = _apiState.value.durationRecommend.toFloat()
            if (targetDistance <= 0) {
                val angle = valueToAngle(value = distanceTarget, maxIndicatorValue = 3.0f)
                _homeUiState.value = _homeUiState.value.copy(
                    distanceTarget = distanceTarget,
                    appliedAngleDistance = angle
                )
                localRepo.updateAppliedAngleDistance(date = date, appliedAngleDistance = angle)

            } else {
                localRepo.updateAppliedAngleDistance(
                    date = date,
                    appliedAngleDistance = _homeUiState.value.appliedAngleDistance
                )
            }
            if (targetDuration <= 0) {
                val angle = valueToAngle(value = durationTarget, maxIndicatorValue = 120f)
                _homeUiState.value = _homeUiState.value.copy(
                    durationTarget = durationTarget,
                    appliedAngleDuration = angle
                )
                localRepo.updateAppliedAngleDuration(date = date, appliedAngleDuration = angle)
            } else {
                localRepo.updateAppliedAngleDuration(
                    date = date,
                    appliedAngleDuration = _homeUiState.value.appliedAngleDuration
                )
            }
            putDataToServer()
        }

    }

    private fun valueToAngle(value: Float, maxIndicatorValue: Float): Float {
        Log.d("angle", "valueToAngle: $value")
        val new_value = value
        val old_min = 0f
        val old_max = 100f
        val new_min = 0f
        val new_max = maxIndicatorValue
        val old_value =
            (((new_value - old_min) * (old_max - old_min)) / (new_max - new_min)) + old_min    //return old_value
        return (old_value * 250) / 100  //change to angle   0 maxIndicator  0 250
    }

    private fun getYesterdayData() {
        viewModelScope.launch {
            val data = localRepo.getStepsData(date = date - 1)
            data?.let {
                _homeUiState.value = _homeUiState.value.copy(
                    calories = it.calories.toInt(),
                    weightLoosed = it.weightLoosed,
                )
                Log.d("subhash", "yesterday: ${_homeUiState.value}")
            }
        }
    }

    private fun updateServerIn24Hr() {
        viewModelScope.launch {
            val list = localRepo.getAllStepsData()
            if (list.isEmpty()) return@launch
            val today = date
            val yesterday = date - 1
            list.forEach {
                when (it.date) {
                    today -> {}
                    yesterday -> {}
                    else -> {
                        val dayData = createPutDayData(
                            bpm = 0,
                            id = "",
                            uid = uid,
                            weightLoose = it.weightLoosed.toFloat(),
                            date_of = "${LocalDate.now().withDayOfMonth(it.date)}",
                            calories = it.calories.toInt(),
                            heartRate = 72,
                            steps = Steps(steps = it.allSteps, unit = "steps"),
                            duration = Duration(dur = it.realtime, unit = "mins"),
                            distance = Distance(dis = it.allSteps.toFloat() / 1408f, unit = "km")
                        )
                        val result = walkingToolRepo.putDayData(putDayData = dayData)
                        Log.d("daydata", "updateServerIn24Hr: ${result.data?.status?.msg}")
                    }
                }
                localRepo.deleteOldData(today, yesterday)
            }
        }
    }

    private fun createPutDayData(
        bpm: Int,
        calories: Int,
        date_of: String,
        distance: Distance,
        duration: Duration,
        heartRate: Int,
        id: String,
        steps: Steps,
        uid: String,
        weightLoose: Float
    ): PutDayData {
        return PutDayData(
            bpm = bpm,
            calories = calories,
            date = date_of,
            distance = distance,
            duration = duration,
            heartRate = heartRate,
            id = id,
            steps = steps,
            uid = uid,
            weightLoose = weightLoose
        )
    }


}