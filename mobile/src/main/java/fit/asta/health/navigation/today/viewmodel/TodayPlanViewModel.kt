package fit.asta.health.navigation.today.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.navigation.today.domain.model.TodayData
import fit.asta.health.scheduler.compose.screen.homescreen.Event
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.AlarmUtils
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmBackendRepo: AlarmBackendRepo,
    private val prefUtils: PrefUtils,
    private val alarmUtils: AlarmUtils
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)
    private val _alarmListNextDay = mutableStateListOf<AlarmEntity>()
    val alarmListNextDay = MutableStateFlow(_alarmListNextDay)

    private val _todayUi = MutableStateFlow(TodayData())
    val todayUi = _todayUi.asStateFlow()
    private val calendar: Calendar = Calendar.getInstance()
    val today: Int = calendar.get(Calendar.DAY_OF_WEEK)

    init {
        getWeatherSunSlots()
        getAlarms()
        val currentTime = LocalTime.now()
        val alarmTime = LocalTime.of(21, 0)
        if (currentTime.isAfter(alarmTime) || currentTime == alarmTime) {
            getNextDayAlarm()
        }
    }

    fun hSEvent(uiEvent: HomeEvent) {
        when (uiEvent) {
            is HomeEvent.EditAlarm -> {
                viewModelScope.launch {
                    prefUtils.setPreferences(
                        key = "alarm",
                        value = uiEvent.alarm.alarmId
                    )
                }
            }

            is HomeEvent.SetAlarm -> {
                viewModelScope.launch { prefUtils.setPreferences(key = "alarm", value = 999) }
            }

            is HomeEvent.DeleteAlarm -> {
                deleteAlarm(uiEvent.alarm, uiEvent.event)
            }

            is HomeEvent.UndoAlarm -> {
                undoAlarm(uiEvent.alarm, uiEvent.event)
            }

            is HomeEvent.SkipAlarm -> {
                skipAlarm(uiEvent.alarm, uiEvent.event)
            }

            is HomeEvent.UndoSkipAlarm -> {
                undoSkipAlarm(uiEvent.alarm, uiEvent.event)
            }
        }
    }

    private fun undoAlarm(alarm: AlarmEntity, event: Event) {
        viewModelScope.launch {
            alarmLocalRepo.insertAlarm(alarm)
            if (alarm.status) {
                alarmUtils.scheduleAlarm(alarm)
            }
            if (alarm.idFromServer.isNotEmpty()) {
                alarmLocalRepo.deleteSyncData(
                    AlarmSync(
                        alarmId = alarm.alarmId,
                        scheduleId = alarm.idFromServer
                    )
                )
            }
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
    }

    private fun deleteAlarm(alarmItem: AlarmEntity, event: Event) {
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
        viewModelScope.launch {
            if (alarmItem.status) alarmUtils.cancelScheduleAlarm(alarmItem, true)
            alarmLocalRepo.deleteAlarm(alarmItem)
            if (alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.insertSyncData(
                    AlarmSync(alarmId = alarmItem.alarmId, scheduleId = alarmItem.idFromServer)
                )
            }
        }
    }

    private fun skipAlarm(alarmItem: AlarmEntity, event: Event) {
        val skipDate = LocalDate.now().dayOfMonth
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
                skipDate.plus(1)
            }
        }
        viewModelScope.launch {
            if (alarmItem.status) alarmUtils.cancelScheduleAlarm(alarmItem, true)
            val alarm = alarmItem.copy(status = false, skipDate = skipDate)
            alarmLocalRepo.updateAlarm(alarm)
        }
    }

    private fun undoSkipAlarm(alarm: AlarmEntity, event: Event) {
        viewModelScope.launch {
            alarmUtils.scheduleAlarm(alarm)
            val alarmItem = alarm.copy(status = true, skipDate = -1)
            alarmLocalRepo.updateAlarm(alarmItem)
            Log.d("alarm", "undoSkipAlarm: alarmOld:$alarm ,alarmItem:$alarmItem")
            when (event) {
                Event.Morning -> {
                    _alarmListMorning.add(alarmItem)
                }

                Event.Afternoon -> {
                    _alarmListAfternoon.add(alarmItem)
                }

                Event.Evening -> {
                    _alarmListEvening.add(alarmItem)
                }

                Event.NextDay -> {
                    _alarmListNextDay.add(alarmItem)
                }
            }
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
                        val today = if (it.week.recurring) {
                            when (today) {
                                Calendar.MONDAY -> {
                                    it.week.monday
                                }

                                Calendar.TUESDAY -> {
                                    it.week.tuesday
                                }

                                Calendar.WEDNESDAY -> {
                                    it.week.wednesday
                                }

                                Calendar.THURSDAY -> {
                                    it.week.thursday
                                }

                                Calendar.FRIDAY -> {
                                    it.week.friday
                                }

                                Calendar.SATURDAY -> {
                                    it.week.saturday
                                }

                                Calendar.SUNDAY -> {
                                    it.week.sunday
                                }

                                else -> false
                            }
                        } else true
                        if (today) {
                            when (it.time.hours.toInt()) {
                                in 0..2 -> {
                                    _alarmListEvening.add(it)
                                }

                                in 3..12 -> {
                                    _alarmListMorning.add(it)
                                }
                                in  13..16->{
                                    _alarmListAfternoon.add(it)
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
                        val nextDay = if (it.week.recurring) {
                            when (today) {
                                Calendar.MONDAY -> {
                                    it.week.tuesday
                                }

                                Calendar.TUESDAY -> {
                                    it.week.wednesday
                                }

                                Calendar.WEDNESDAY -> {
                                    it.week.thursday
                                }

                                Calendar.THURSDAY -> {
                                    it.week.friday
                                }

                                Calendar.FRIDAY -> {
                                    it.week.saturday
                                }

                                Calendar.SATURDAY -> {
                                    it.week.sunday
                                }

                                Calendar.SUNDAY -> {
                                    it.week.monday
                                }

                                else -> false
                            }
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
        viewModelScope.launch {
            alarmBackendRepo.getTodayDataFromBackend(
                userID = "6309a9379af54f142c65fbfe",
                date = "2023-07-03",
                location = "bangalore",
                latitude = 28.6353f,
                longitude = 7.2250f
            ).collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        result.data?.let { todayData ->
                            Log.d("today", "getWeatherSunSlots: $todayData")
                            _todayUi.update {
                                it.copy(
                                    date = todayData.date,
                                    weatherCode = todayData.weatherCode,
                                    location = todayData.location,
                                    weatherCodeList = todayData.weatherCodeList,
                                    temperatureList = todayData.temperatureList,
                                    temperature = todayData.temperature
                                )
                            }
                        }
                    }

                    is NetworkResult.Loading -> {}
                    is NetworkResult.Error -> {
                        Log.d("today", "getWeatherSunSlots: ${result.message}")
                    }
                }
            }
        }
    }
}