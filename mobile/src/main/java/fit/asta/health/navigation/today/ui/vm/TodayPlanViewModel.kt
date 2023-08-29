package fit.asta.health.navigation.today.ui.vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.UiState
import fit.asta.health.common.utils.getCurrentDate
import fit.asta.health.common.utils.toUiState
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.model.TodayData
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.repo.AlarmBackendRepo
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.navigation.today.ui.view.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmBackendRepo: AlarmBackendRepo,
    private val prefManager: PrefManager,
    private val stateManager: StateManager
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)
    private val _alarmListNextDay = mutableStateListOf<AlarmEntity>()
    val alarmListNextDay = MutableStateFlow(_alarmListNextDay)

    private val calendar: Calendar = Calendar.getInstance()
    val today: Int = calendar.get(Calendar.DAY_OF_WEEK)

    private val _todayState =
        MutableStateFlow<UiState<TodayData>>(UiState.Idle)
    val todayState = _todayState.asStateFlow()

    init {
        getWeatherSunSlots()
        getAlarms()
        val currentTime = LocalTime.now()
        val alarmTime = LocalTime.of(21, 0)
        if (currentTime.isAfter(alarmTime) || currentTime == alarmTime) {
            getNextDayAlarm()
        }
    }


    fun setAlarmPreferences(value: Long) {
        viewModelScope.launch {
            prefManager.setAlarmId(value)
        }
    }

    fun getDefaultSchedule(context: Context) {
        when (_todayState.value) {
            is UiState.Success -> {
                (_todayState.value as UiState.Success<TodayData>).data.schedule.forEach { alarmEntity ->
                    val alarm = alarmEntity.copy(
                        meta = Meta(
                            cBy = alarmEntity.meta.cBy,
                            cDate = Calendar.getInstance().toString(),
                            sync = 1,
                            uDate = Calendar.getInstance().toString()
                        ), status = true,
                        idFromServer = "",
                        alarmId = (System.currentTimeMillis() + AtomicLong().incrementAndGet())
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

    fun undo(alarm: AlarmEntity, event: Event) {
        when (event) {
            Event.Morning -> {
                _alarmListMorning.add(alarm)
            }

            Event.Afternoon -> {
                _alarmListAfternoon.add(alarm)
            }

            Event.Evening -> {
                _alarmListEvening.add(alarm)
            }

            Event.NextDay -> {
                _alarmListNextDay.add(alarm)
            }
        }
    }

    fun removeAlarm(alarmItem: AlarmEntity, event: Event) {
        when (event) {
            Event.Morning -> {
                _alarmListMorning.remove(alarmItem)
            }

            Event.Afternoon -> {
                _alarmListAfternoon.remove(alarmItem)
            }

            Event.Evening -> {
                _alarmListEvening.remove(alarmItem)
            }

            Event.NextDay -> {
                _alarmListNextDay.remove(alarmItem)
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
                            uDate = Calendar.getInstance().toString()
                        )
                    )
                )
            } else {
                alarmLocalRepo.deleteAlarm(alarmItem)
            }
            Log.d("today", "deleteAlarm: done")
        }
    }

    fun skipAlarm(alarmItem: AlarmEntity, context: Context) {
        val skipDate = LocalDate.now().dayOfMonth
        viewModelScope.launch {
            if (alarmItem.status) stateManager.skipAlarmTodayOnly(context, alarmItem)
            val alarm = alarmItem.copy(status = false, skipDate = skipDate)
            alarmLocalRepo.updateAlarm(alarm)
            Log.d("today", "skipAlarm: done")
        }
    }


    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                _alarmListMorning.clear()
                _alarmListAfternoon.clear()
                _alarmListEvening.clear()
                Log.d("alarm", "getAlarms: $list,day ${today}")
                list.forEach {
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
            }
        }
    }

    private fun getNextDayAlarm() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                _alarmListNextDay.clear()
                list.forEach {
                    if (it.status) {
                        val nextDay = if (it.daysOfWeek.isRepeating) {
                            it.daysOfWeek.isBitOn(today + 1)
                        } else false
                        if (nextDay) {
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
            _todayState.value = alarmBackendRepo.getTodayDataFromBackend(
                userID = "6309a9379af54f142c65fbfe",
                date = getCurrentDate(),
                location = "jaipur",
                latitude = 26.835598f,
                longitude = 75.541794f
            ).toUiState()
        }
    }

    fun retry() {
        getWeatherSunSlots()
    }
}