package fit.asta.health.feature.scheduler.services


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.Utils.CHANGE_STATE_ACTION
import fit.asta.health.feature.scheduler.util.Utils.DISMISS_ACTION
import fit.asta.health.feature.scheduler.util.Utils.SKIP_ALARM_ACTION
import fit.asta.health.feature.scheduler.util.Utils.SNOOZE_ACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var stateManager: StateManager

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action || Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action
        ) {
            val toastText = String.format("Alarm Reboot")
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            startRescheduleAlarmsService(context)
        }
        when (intent.action) {
            SNOOZE_ACTION -> {
//                LogUtils.v("AlarmStateManager received SNOOZE_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.setSnoozeAlarm(context, it)
                    stateManager.stopService(context)
                }
            }

            DISMISS_ACTION -> {
//                LogUtils.v("AlarmStateManager received DISMISS_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.dismissAlarm(context, it)
                    stateManager.stopService(context)
                }
            }

            SKIP_ALARM_ACTION -> {
//                LogUtils.v("AlarmStateManager received SKIP_ALARM_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.skipAlarmSetPreEndNotification(context, it)
                }
            }

            CHANGE_STATE_ACTION -> {
                stateManager.handleIntent(context, intent)
            }

            else -> {}
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        val intentService = Intent(context, RescheduleAlarmService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService)
        } else {
            context.startService(intentService)
        }
    }
}