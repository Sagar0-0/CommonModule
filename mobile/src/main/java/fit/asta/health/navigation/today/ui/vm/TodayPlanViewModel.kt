package fit.asta.health.navigation.today.ui.vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.auth.di.UID
import fit.asta.health.auth.repo.AuthRepo
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.db.AlarmInstanceDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.db.entity.Weekdays
import fit.asta.health.data.scheduler.remote.getTodayData
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmBackendRepo: AlarmBackendRepo,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val prefManager: PrefManager,
    private val stateManager: StateManager,
    @UID private val uId: String,
    private val authRepo: AuthRepo
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)
    private val _alarmListNextDay = mutableStateListOf<AlarmEntity>()
    val alarmListNextDay = MutableStateFlow(_alarmListNextDay)
    private val _defaultScheduleVisibility = MutableStateFlow(false)
    val defaultScheduleVisibility = _defaultScheduleVisibility.asStateFlow()
    private val calendar: Calendar = Calendar.getInstance()
    val today: Int = calendar.get(Calendar.DAY_OF_WEEK)

    private val _todayState = MutableStateFlow<UiState<TodayData>>(UiState.Idle)
    val todayState = _todayState.asStateFlow()

    private val currentTime: LocalTime = LocalTime.now()

    init {
        getWeatherSunSlots()
        getAlarms()
    }

    fun getUserName(): String {
        return authRepo.getUser()?.name ?: "Aastha"
    }

    fun setAlarmPreferences(value: Long) {
        viewModelScope.launch {
            prefManager.setAlarmId(value)
        }
    }

    fun getDefaultSchedule(context: Context) {
        viewModelScope.launch {
            when (val result = alarmBackendRepo.getDefaultSchedule(uId)) {
                is ResponseState.Success -> {
                    result.data.schedule.forEach { alarmEntity ->
                        val alarm = alarmEntity.copy(
                            meta = Meta(
                                cBy = alarmEntity.meta.cBy,
                                cDate = getCurrentTime(),
                                sync = 1,
                                uDate = getCurrentTime()
                            ),
                            idFromServer = "",
                            userId = uId,
                            daysOfWeek = Weekdays.ALL,
//                        alarmId = (System.currentTimeMillis() + AtomicLong().incrementAndGet())
                        )
                        stateManager.registerAlarm(context, alarm)
                        viewModelScope.launch {
                            alarmLocalRepo.insertAlarm(alarm)
                        }
                    }
                }

                else -> {}
            }
        }
    }


    fun deleteAlarm(alarmItem: AlarmEntity, context: Context) {
        viewModelScope.launch {
            if (alarmItem.status) stateManager.deleteAlarm(context, alarmItem)
            if (alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.updateAlarm(
                    alarmItem.copy(
                        status = false,
                        meta = Meta(
                            cBy = alarmItem.meta.cBy,
                            cDate = alarmItem.meta.cDate,
                            sync = 2,
                            uDate = getCurrentTime()
                        )
                    )
                )
            } else {
                alarmLocalRepo.deleteAlarm(alarmItem)
                alarmInstanceDao.getInstancesByAlarmId(alarmItem.alarmId)?.let {
                    alarmInstanceDao.delete(it)
                }
            }
            Log.d("today", "deleteAlarm: done")
        }
    }

    fun skipAlarm(alarmItem: AlarmEntity, context: Context) {
        val skipDate = LocalDate.now().dayOfMonth
        viewModelScope.launch {
            stateManager.skipAlarmTodayOnly(context, alarmItem)
            alarmLocalRepo.updateAlarm(alarmItem.copy(skipDate = skipDate))
            Log.d("today", "skipAlarm: done")
        }
    }


    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                _defaultScheduleVisibility.value = list.isEmpty()
                _alarmListMorning.clear()
                _alarmListAfternoon.clear()
                _alarmListEvening.clear()
                Log.d("alarm", "getAlarms: $list,day ${today}")
                val alarmList = mutableStateListOf<AlarmEntity>()
                alarmList.clear()
                alarmList.addAll(list.filter {
                    currentTime.isBefore(LocalTime.of(it.time.hours, it.time.minutes))
                })
                alarmList.forEach {
                    if (it.status && it.skipDate != LocalDate.now().dayOfMonth) {
                        val today = if (it.daysOfWeek.isRepeating) {
                            it.daysOfWeek.isBitOn(today)
                        } else true
                        if (today) {
                            when (it.time.hours) {
                                in 0..2 -> {
                                    if (it.time.minutes > 0) _alarmListMorning.add(it)
                                    else _alarmListEvening.add(it)
                                }

                                in 3..12 -> {
                                    if (it.time.minutes > 0) _alarmListAfternoon.add(it)
                                    else _alarmListMorning.add(it)
                                }

                                in 13..16 -> {
                                    if (it.time.minutes > 0) _alarmListEvening.add(it)
                                    else _alarmListAfternoon.add(it)
                                }

                                in 17..23 -> {
                                    _alarmListEvening.add(it)
                                }
                            }
                        }
                    }
                }

                val alarmTime = LocalTime.of(21, 0)
                if (currentTime.isAfter(alarmTime) || currentTime == alarmTime) {
                    _alarmListNextDay.clear()
                    var nextDay = today + 1
                    if (nextDay > Calendar.SATURDAY) {
                        nextDay = Calendar.SUNDAY
                    }
                    list.forEach {
                        if (it.status && it.daysOfWeek.isRepeating &&
                            it.daysOfWeek.isBitOn(nextDay)
                        ) {
                            _alarmListNextDay.add(it)
                        }
                    }
                }
            }
        }
    }


    private fun getWeatherSunSlots() {
        _todayState.value = UiState.Loading
        viewModelScope.launch {
            prefManager.address.collectLatest {
                viewModelScope.launch {
                    _todayState.value = when (val result = alarmBackendRepo.getTodayDataFromBackend(
                        userID = uId,
                        date = getCurrentDate(),
                        location = it.currentAddress,
                        latitude = it.lat.toFloat(),
                        longitude = it.long.toFloat()
                    )) {
                        is ResponseState.Success -> {
                            UiState.Success(result.data.getTodayData())
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
    }
}