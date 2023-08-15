package fit.asta.health.scheduler.viewmodel

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.compose.screen.alarmscreen.AlarmEvent
import fit.asta.health.scheduler.compose.screen.alarmscreen.AlarmUiState
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.AlarmUtils
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.services.AlarmService
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class AlarmScreenViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val alarmUtils: AlarmUtils
) : ViewModel() {

    var alarmEntity: AlarmEntity? = null
    private var variantInterval: Stat? = null

    private val _alarmUiState = mutableStateOf(AlarmUiState())
    val alarmUiState: State<AlarmUiState> = _alarmUiState
    fun setAlarmAndVariant(alarm: AlarmEntity?, vInterval: Stat?) {
        Log.d("TAGTAG", "setAlarmAndVariant:alarm $alarm,\n variant $vInterval ")
        alarm?.let {
            alarmEntity = it
            _alarmUiState.value = _alarmUiState.value.copy(
                image = it.info.url,
                alarmName = it.info.name,
                alarmTime = if (it.time.midDay) {
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d pm",
                        it.time.hours.toInt(),
                        it.time.minutes.toInt()
                    )
                } else {
                    String.format(
                        Locale.getDefault(),
                        "%02d:%02d am",
                        it.time.hours.toInt(),
                        it.time.minutes.toInt()
                    )
                }
            )
        }
        vInterval?.let { variantInterval = it }

    }

    fun event(uiEvent: AlarmEvent) {
        when (uiEvent) {
            is AlarmEvent.onSwipedLeft -> {
                alarmEntity?.let { alarm ->
                    if (!alarm.week.recurring) updateState(alarm)
                    else setNextAlarm(alarm, variantInterval)
                    alarm.info.name = "Snooze ${alarm.info.name}"
                    alarmUtils.snooze(alarm)
                    Log.d("TAGTAGTAG", "snooze: ")
                }
                val intentService =
                    Intent(uiEvent.context, AlarmService::class.java)
                uiEvent.context.stopService(intentService)
                (uiEvent.context as AppCompatActivity).finishAndRemoveTask()
            }

            is AlarmEvent.onSwipedRight -> {
                alarmEntity?.let { alarm ->
                    if (alarm.interval.isRemainderAtTheEnd) {
                        setPostNotification(alarm, variantInterval)
                    }
                    if (!alarm.week.recurring) updateState(alarm)
                    else setNextAlarm(alarm, variantInterval)
                    Log.d("TAGTAGTAG", "stop: ")
                }
                val intentService =
                    Intent(uiEvent.context, AlarmService::class.java)
                uiEvent.context.stopService(intentService)
                (uiEvent.context as AppCompatActivity).finishAndRemoveTask()
            }
        }
    }

    fun updateState(alarm: AlarmEntity) {
        viewModelScope.launch {
            alarmLocalRepo.updateAlarm(alarm.copy(status = false))
        }
    }

    private fun setNextAlarm(alarm: AlarmEntity, variantInt: Stat?) {
        if (alarm.alarmId == 999) {
            return
        } // alarm is snooze in past so no need to reschedule
        if (variantInt == null) {
            alarmUtils.scheduleNextAlarm(alarm)
            alarmUtils.schedulerAlarmNextPreNotification(alarm, false, null, alarm.alarmId)
        } else {
            alarmUtils.scheduleNextIntervalAlarm(alarm, variantInt)
            alarmUtils.schedulerAlarmNextPreNotification(alarm, true, variantInt, variantInt.id)
        }
    }

    private fun setPostNotification(alarm: AlarmEntity, variantInt: Stat?) {
        if (alarm.alarmId == 999) {
            return
        }
        if (variantInt == null) {
            alarmUtils.schedulerAlarmPostNotification(
                alarm,
                isInterval = true,
                interval = null,
                alarm.alarmId
            )
        } else {
            alarmUtils.schedulerAlarmPostNotification(
                alarm,
                isInterval = true,
                variantInt,
                variantInt.id
            )
        }
    }
}