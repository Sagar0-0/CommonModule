package fit.asta.health.navigation.today.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.scheduler.compose.screen.homescreen.Event
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val prefUtils: PrefUtils
) : ViewModel() {
    private val _alarmListMorning = mutableStateListOf<AlarmEntity>()
    val alarmListMorning = MutableStateFlow(_alarmListMorning)
    private val _alarmListAfternoon = mutableStateListOf<AlarmEntity>()
    val alarmListAfternoon = MutableStateFlow(_alarmListAfternoon)
    private val _alarmListEvening = mutableStateListOf<AlarmEntity>()
    val alarmListEvening = MutableStateFlow(_alarmListEvening)


    init {
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
                deleteAlarm(uiEvent.alarm, uiEvent.context,uiEvent.event)
            }

            is HomeEvent.UndoAlarm -> {
                undoAlarm(uiEvent.alarm, uiEvent.context,uiEvent.event)
            }

            else -> {}
        }
    }

    private fun undoAlarm(alarm: AlarmEntity, context: Context,event: Event) {
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
            when(event){
                Event.Morning->{_alarmListMorning.add(alarm)}
                Event.Afternoon->{_alarmListAfternoon.add(alarm)}
                Event.Evening->{_alarmListEvening.add(alarm)}
            }
        }
    }

    private fun deleteAlarm(alarmItem: AlarmEntity, context: Context,event: Event) {
        when(event){
            Event.Morning->{_alarmListMorning.remove(alarmItem)}
            Event.Afternoon->{_alarmListAfternoon.remove(alarmItem)}
            Event.Evening->{_alarmListEvening.remove(alarmItem)}
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

    private fun getAlarms() {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect { list ->
                _alarmListMorning.clear()
                _alarmListAfternoon.clear()
                _alarmListEvening.clear()
                val day =LocalDate.now().dayOfWeek.value
                Log.d("list", "getAlarms: $list,day $day")
                list.forEach{
                    if (it.status) {
                        val today= if (it.week.recurring){
                            when(day){
                                1->{it.week.monday}
                                2->{it.week.tuesday}
                                3->{it.week.wednesday}
                                4->{it.week.thursday}
                                5->{it.week.friday}
                                6->{it.week.saturday}
                                7->{it.week.sunday}
                                else ->false
                            }
                        } else true
                        if (today){
                            when(it.time.hours.toInt()) {
                                in  0..3->{
                                    _alarmListEvening.add(it)
                                }
                                in  4..12->{
                                    _alarmListMorning.add(it)
                                }
                                in  13..16->{
                                    _alarmListAfternoon.add(it)
                                }
                                in  17..23->{
                                    _alarmListEvening.add(it)
                                }
                            }
                        }
                    }
                }
            }
        }
        //morning 3 12
        // afternoon 12 4
        // even 4  3

    }
}