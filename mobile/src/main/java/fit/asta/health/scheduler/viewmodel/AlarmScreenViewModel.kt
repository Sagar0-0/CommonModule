package fit.asta.health.scheduler.viewmodel

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.compose.screen.alarmscreen.AlarmEvent
import fit.asta.health.scheduler.compose.screen.alarmscreen.AlarmUiState
import fit.asta.health.scheduler.model.AlarmBackendRepo
import fit.asta.health.scheduler.model.AlarmLocalRepo
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import fit.asta.health.scheduler.model.net.scheduler.Stat
import fit.asta.health.scheduler.services.AlarmService
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AlarmScreenViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val backendRepo: AlarmBackendRepo,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var alarmEntity: AlarmEntity? = null
    var variantInterval: Stat? = null

    private val _alarmUiState = mutableStateOf(AlarmUiState())
    val alarmUiState: State<AlarmUiState> = _alarmUiState
    fun setAlarmAndVariant(alarm: AlarmEntity?, vInterval: Stat?) {
        alarm?.let {
            alarmEntity = it
            _alarmUiState.value = _alarmUiState.value.copy(
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
                val calendar: Calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()
                calendar.add(Calendar.MINUTE, 5)
                Log.d(
                    "TAGTAGTAG", "onSwipedLeft::  " +
                            "${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)} "
                )
                alarmEntity?.let {
                    it.time.hours = calendar.get(Calendar.HOUR_OF_DAY).toString()
                    it.time.minutes = calendar.get(Calendar.MINUTE).toString()
                    it.info.name = "Snooze ${it.info.name}"
                    it.scheduleAlarm(uiEvent.context)
                }
                val intentService =
                    Intent(uiEvent.context, AlarmService::class.java)
                uiEvent.context.stopService(intentService)
                (uiEvent.context as AppCompatActivity).finishAndRemoveTask()
            }
            is AlarmEvent.onSwipedRight -> {
//                alarmEntity?.let { alarm ->
//                    variantInterval?.let { variant ->
//                        alarm.cancelAlarmInterval(
//                            uiEvent.context,
//                            variant
//                        )
//                        if (alarm.interval.isRemainderAtTheEnd) {
//                            alarm.schedulerPostNotification(
//                                uiEvent.context,
//                                true,
//                                variant,
//                                variant.id
//                            )
//                        }
//                    }
//                    if (variantInterval == null) {
//                        alarm.cancelScheduleAlarm(
//                            uiEvent.context,
//                            alarm.alarmId,
//                            false
//                        )
//                        if (alarm.week.recurring) {
//                            viewModelScope.launch {
//                                backendRepo.updateScheduleDataOnBackend(alarm.copy(status = true))
//                                alarmLocalRepo.updateAlarm(alarm.copy(status = true))
//                            }
//
//                            alarmEntity!!.scheduleAlarm(uiEvent.context)
//                        } else {
//                            viewModelScope.launch {
//                                backendRepo.updateScheduleDataOnBackend(alarm.copy(status = false))
//                                alarmLocalRepo.updateAlarm(alarm.copy(status = false))
//                            }
//                        }
//                        if (alarm.interval.isRemainderAtTheEnd) {
//                            alarm.schedulerPostNotification(
//                                uiEvent.context,
//                                false,
//                                null,
//                                alarm.alarmId
//                            )
//                        }
//                    }
//                    Log.d("TAGTAGTAG", "onSwipedRight: ")
//                }
                val intentService =
                    Intent(uiEvent.context, AlarmService::class.java)
                uiEvent.context.stopService(intentService)
                (uiEvent.context as AppCompatActivity).finishAndRemoveTask()
            }
            else -> {}
        }
    }

}