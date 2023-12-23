package fit.asta.health.feature.scheduler.services


import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_BOOT_COMPLETED
import android.content.Intent.ACTION_MY_PACKAGE_REPLACED
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.data.walking.service.StepService
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
        context?.run {
            if (intent?.action == ACTION_BOOT_COMPLETED && hasPermissions(context)) {
                val launchIntent = Intent(applicationContext, StepService::class.java)
                ContextCompat.startForegroundService(applicationContext, launchIntent)
            }
        }
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

    private fun hasPermissions(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (!hasPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)) {
                return false
            }
        }
        return true
    }

    @Suppress("SameParameterValue")
    private fun hasPermission(context: Context, permission: String): Boolean {
        val status = ContextCompat.checkSelfPermission(context, permission)
        return status == PackageManager.PERMISSION_GRANTED
    }
}