package fit.asta.health.navigation.today.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.PrefUtils
import fit.asta.health.scheduler.compose.screen.homescreen.HomeEvent
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.db.entity.AlarmSync
import fit.asta.health.thirdparty.spotify.utils.SpotifyConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayPlanViewModel@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val prefUtils: PrefUtils
): ViewModel() {
    private val _alarmList = mutableStateListOf<AlarmEntity>()
    val alarmList = MutableStateFlow(_alarmList)


    init {
        getAlarms()
    }
    fun hSEvent(uiEvent: HomeEvent) {
        when (uiEvent) {
            is HomeEvent.EditAlarm -> {
                viewModelScope.launch { prefUtils.setPreferences(key = "alarm", value = uiEvent.alarm.alarmId)  }
            }

            is HomeEvent.SetAlarm -> {
                viewModelScope.launch { prefUtils.setPreferences(key = "alarm", value = 999)  }
            }

            is HomeEvent.DeleteAlarm -> {
                deleteAlarm(uiEvent.alarm, uiEvent.context)
            }

            is HomeEvent.UndoAlarm -> {
                undoAlarm(uiEvent.alarm, uiEvent.context)
            }

            else -> {}
        }
    }

    private fun undoAlarm(alarm: AlarmEntity, context: Context) {
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
            _alarmList.add(alarm)
        }
    }

    private fun deleteAlarm(alarmItem: AlarmEntity, context: Context) {
        _alarmList.remove(alarmItem)
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
                Log.d(SpotifyConstants.TAG, " list result: $list")
                _alarmList.clear()
                _alarmList.addAll(list)
            }
        }

    }
}