package fit.asta.health.feature.scheduler.services


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_MY_PACKAGE_REPLACED
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.feature.scheduler.util.StateManager
import fit.asta.health.feature.scheduler.util.Utils.CHANGE_STATE_ACTION
import fit.asta.health.feature.scheduler.util.Utils.DISMISS_ACTION
import fit.asta.health.feature.scheduler.util.Utils.SKIP_ALARM_ACTION
import fit.asta.health.feature.scheduler.util.Utils.SNOOZE_ACTION
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var stateManager: StateManager
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_MY_PACKAGE_REPLACED -> {
                startRescheduleAlarmsService(context!!)
            }

            ACTION_BOOT_COMPLETED -> {
                startRescheduleAlarmsService(context!!)
            }

            SNOOZE_ACTION -> {
                intent.getLongExtra("id", -1).let {
                    stateManager.setSnoozeAlarm(context!!, it)
                    stateManager.stopService(context)
                }
            }

            DISMISS_ACTION -> {
                intent.getLongExtra("id", -1).let {
                    stateManager.dismissAlarm(context!!, it)
                    stateManager.stopService(context)
                }
            }

            SKIP_ALARM_ACTION -> {
                intent.getLongExtra("id", -1).let {
                    stateManager.skipAlarmSetPreEndNotification(context!!, it)
                }
            }

            CHANGE_STATE_ACTION -> {
                stateManager.handleIntent(context!!, intent)
            }

            else -> {}
        }
    }

    private fun startRescheduleAlarmsService(context: Context) {
        Log.d("TAG", "startRescheduleAlarmsService: ")
        val toastText = String.format("Alarm Reboot")
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
        stateManager.rescheduleAlarm(context)
    }
}