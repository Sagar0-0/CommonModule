package fit.asta.health.navigation.alarms

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.db.AlarmInstanceDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AllAlarmViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val stateManager: StateManager,
    private val prefManager: PrefManager,
    private val alarmInstanceDao: AlarmInstanceDao,

    ) : ViewModel() {
    private val _alarmList = mutableStateListOf<AlarmEntity>()
    val alarmList = MutableStateFlow(_alarmList)

    init {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect {
                _alarmList.clear()
                _alarmList.addAll(it)
            }
        }
    }

    fun setAlarmPreferences(value: Long) {
        viewModelScope.launch {
            prefManager.setAlarmId(value)
        }
    }

    fun changeAlarmState(state: Boolean, alarm: AlarmEntity, context: Context) {
        viewModelScope.launch {
            Log.d("state", "changeAlarmState: $state")
            updateDatabase(state, alarm)
            if (state) {
                stateManager.registerAlarm(
                    context,
                    alarm,
                    (alarm.skipDate == LocalDate.now().dayOfMonth)
                )
            } else {
                stateManager.deleteAlarm(context, alarm)
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

    private fun updateDatabase(state: Boolean, alarm: AlarmEntity) {
        viewModelScope.launch {
            alarmLocalRepo.updateAlarm(
                alarm.copy(
                    status = state,
                    meta = Meta(
                        cBy = alarm.meta.cBy,
                        cDate = alarm.meta.cDate,
                        uDate = getCurrentTime(),
                        sync = 1
                    )
                )
            )
        }
    }
}