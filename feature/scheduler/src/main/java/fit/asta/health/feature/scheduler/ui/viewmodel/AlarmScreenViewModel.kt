package fit.asta.health.feature.scheduler.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.data.scheduler.local.model.AlarmEntity
import fit.asta.health.data.scheduler.repo.AlarmLocalRepo
import fit.asta.health.feature.scheduler.services.AlarmService
import fit.asta.health.feature.scheduler.ui.screen.alarmscreen.AlarmUiState
import fit.asta.health.feature.scheduler.util.StateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class AlarmScreenViewModel
@Inject constructor(
    private val alarmLocalRepo: AlarmLocalRepo,
    private val stateManager: StateManager
) : ViewModel() {

    private var alarmEntity: AlarmEntity? = null

    private val _alarmUiState = MutableStateFlow(AlarmUiState())
    val alarmUiState = _alarmUiState.asStateFlow()


    fun stop(context: Context) {
        stateManager.dismissAlarm(context, alarmEntity!!.alarmId)
        val intentService =
            Intent(context, AlarmService::class.java)
        context.stopService(intentService)
        (context as AppCompatActivity).finishAndRemoveTask()
    }

    fun snooze(context: Context) {
        stateManager.setSnoozeAlarm(context, alarmEntity!!.alarmId)
        val intentService =
            Intent(context, AlarmService::class.java)
        context.stopService(intentService)
        (context as AppCompatActivity).finishAndRemoveTask()
    }

    fun setAlarmUi(alarmId: Long) {
        viewModelScope.launch {
            alarmLocalRepo.getAlarm(alarmId)?.let {
                alarmEntity = it
                _alarmUiState.value = _alarmUiState.value.copy(
                    image = it.info.url,
                    alarmName = it.info.name,
                    alarmTime = if (it.time.hours > 12) {
                        String.format(
                            Locale.getDefault(),
                            "%02d:%02d pm",
                            it.time.hours,
                            it.time.minutes
                        )
                    } else {
                        String.format(
                            Locale.getDefault(),
                            "%02d:%02d am",
                            it.time.hours,
                            it.time.minutes
                        )
                    }
                )
            }
        }
    }
}