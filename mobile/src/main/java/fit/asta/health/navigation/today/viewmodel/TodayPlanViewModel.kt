package fit.asta.health.navigation.today.viewmodel

import android.content.Context
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
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmBackendRepo: AlarmBackendRepo,
    private val prefUtils: PrefUtils
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)

    private val _todayUi = MutableStateFlow(TodayData())
    val todayUi = _todayUi.asStateFlow()


    init {
        getWeatherSunSlots()
        getAlarms()
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
                deleteAlarm(uiEvent.alarm, uiEvent.context, uiEvent.event)
            }

            is HomeEvent.UndoAlarm -> {
                undoAlarm(uiEvent.alarm, uiEvent.context, uiEvent.event)
            }

            is HomeEvent.SkipAlarm -> {
                skipAlarm(uiEvent.alarm, uiEvent.context, uiEvent.event)
            }

            is HomeEvent.UndoSkipAlarm -> {
                undoSkipAlarm(uiEvent.alarm, uiEvent.context, uiEvent.event)
            }
        }
    }

    private fun undoAlarm(alarm: AlarmEntity, context: Context, event: Event) {
        viewModelScope.launch {
            alarmLocalRepo.insertAlarm(alarm)
            if (alarm.status) {
                alarm.scheduleAlarm(context)
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
            }
        }
    }

    private fun deleteAlarm(alarmItem: AlarmEntity, context: Context, event: Event) {
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
        }
        viewModelScope.launch {
            if (alarmItem.status) alarmItem.cancelScheduleAlarm(
                context, alarmItem.alarmId, true
            )
            alarmLocalRepo.deleteAlarm(alarmItem)
            if (alarmItem.idFromServer.isNotEmpty()) {
                alarmLocalRepo.insertSyncData(
                    AlarmSync(alarmId = alarmItem.alarmId, scheduleId = alarmItem.idFromServer)
                )
            }
        }
    }

    private fun skipAlarm(alarmItem: AlarmEntity, context: Context, event: Event) {
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
        }
        viewModelScope.launch {
            if (alarmItem.status) alarmItem.cancelScheduleAlarm(
                context, alarmItem.alarmId, true
            )
            val alarm = alarmItem.copy(status = false, skipDate = LocalDate.now().dayOfMonth)
            alarmLocalRepo.updateAlarm(alarm)
        }
    }

    private fun undoSkipAlarm(alarm: AlarmEntity, context: Context, event: Event) {
        viewModelScope.launch {
            alarm.scheduleAlarm(context)
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
            }
        }
    }

    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                _alarmListMorning.clear()
                _alarmListAfternoon.clear()
                _alarmListEvening.clear()
                val day = LocalDate.now().dayOfWeek.value
                Log.d("alarm", "getAlarms: $list,day $day")
                list.forEach {
                    if (it.status && it.skipDate != LocalDate.now().dayOfMonth) {
                        val today = if (it.week.recurring) {
                            when (day) {
                                1 -> {
                                    it.week.monday
                                }

                                2 -> {
                                    it.week.tuesday
                                }

                                3 -> {
                                    it.week.wednesday
                                }

                                4 -> {
                                    it.week.thursday
                                }

                                5 -> {
                                    it.week.friday
                                }

                                6 -> {
                                    it.week.saturday
                                }

                                7 -> {
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