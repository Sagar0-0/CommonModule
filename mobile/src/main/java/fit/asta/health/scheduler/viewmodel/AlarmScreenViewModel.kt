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
) : ViewModel() {

    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null

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

                Log.d("TAGTAGTAG", "onSwipedLeft::  ")
                alarmEntity?.let {
                    it.info.name = "Snooze ${it.info.name}"
                    it.snooze(uiEvent.context)
                }
                val intentService =
                    Intent(uiEvent.context, AlarmService::class.java)
                uiEvent.context.stopService(intentService)
                (uiEvent.context as AppCompatActivity).finishAndRemoveTask()
            }

            is AlarmEvent.onSwipedRight -> {
                Log.d("TAGTAG", "event: alarm $alarmEntity")
                alarmEntity?.let { alarm ->
                    if (alarm.interval.isRemainderAtTheEnd) {
                        variantInterval?.let { variant ->
                            Log.d("TAGTAG", "event: variant Post natification")
                            alarm.schedulerAlarmPostNotification(
                                uiEvent.context,
                                true,
                                variant,
                                variant.id
                            )
                        }
                        if (variantInterval == null) {
                            Log.d("TAGTAG", "event: alarm Post natification")
                            alarm.schedulerAlarmPostNotification(
                                uiEvent.context,
                                false,
                                null,
                                alarm.alarmId
                            )
                        }
                    }
                    if (!alarm.week.recurring) updateState(alarm)
                    Log.d("TAGTAGTAG", "onSwipedRight: ")
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

}