package fit.asta.health.scheduler.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.data.api.net.scheduler.Stat
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import fit.asta.health.scheduler.data.repo.AlarmLocalRepo
import fit.asta.health.scheduler.data.repo.AlarmUtils
import fit.asta.health.scheduler.services.AlarmService
import fit.asta.health.scheduler.ui.screen.alarmscreen.AlarmUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _alarmUiState = MutableStateFlow(AlarmUiState())
    val alarmUiState = _alarmUiState.asStateFlow()
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


    fun stop(context: Context) {
        alarmEntity?.let { alarm ->
            if (alarm.interval.isRemainderAtTheEnd) {
                setPostNotification(alarm, variantInterval)
            }
            if (!alarm.week.recurring) updateState(alarm)
            else setNextAlarm(alarm, variantInterval)
            Log.d("TAGTAGTAG", "stop: ")
        }
        val intentService =
            Intent(context, AlarmService::class.java)
        context.stopService(intentService)
        (context as AppCompatActivity).finishAndRemoveTask()
    }

    fun snooze(context: Context) {
        alarmEntity?.let { alarm ->
            if (!alarm.week.recurring) updateState(alarm)
            else setNextAlarm(alarm, variantInterval)
            alarm.info.name = "Snooze ${alarm.info.name}"
            alarmUtils.snooze(alarm)
            Log.d("TAGTAGTAG", "snooze: ")
        }
        val intentService =
            Intent(context, AlarmService::class.java)
        context.stopService(intentService)
        (context as AppCompatActivity).finishAndRemoveTask()
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