package fit.asta.health.navigation.today.ui.vm

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.common.utils.getCurrentTime
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.remote.net.scheduler.Meta
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllAlarmViewModel @Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val stateManager: StateManager
) : ViewModel() {
    private val _alarmList = mutableStateListOf<AlarmEntity>()
    val alarmList = MutableStateFlow(_alarmList)

    init {
        viewModelScope.launch {
            alarmLocalRepo.getAllAlarm().collect {
                Log.d("state", "changeAlarmState: ")
                _alarmList.clear()
                _alarmList.addAll(it)
            }
        }
    }

    fun changeAlarmState(state: Boolean, alarm: AlarmEntity, context: Context) {
        viewModelScope.launch {
            Log.d("state", "changeAlarmState: $state")
            updateDatabase(state, alarm)
            if (state) {
                stateManager.registerAlarm(context, alarm)
            } else {
                stateManager.deleteAlarm(context, alarm)
            }
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