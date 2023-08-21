package fit.asta.health.scheduler.ref.alarms


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.newalarm.StateManager
import fit.asta.health.scheduler.ref.newalarm.Utils.CHANGE_STATE_ACTION
import fit.asta.health.scheduler.ref.newalarm.Utils.DISMISS_ACTION
import fit.asta.health.scheduler.ref.newalarm.Utils.SKIP_ALARM_ACTION
import fit.asta.health.scheduler.ref.newalarm.Utils.SNOOZE_ACTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject

@AndroidEntryPoint
class AlarmStateManager : BroadcastReceiver() {
    @Inject
    lateinit var stateManager: StateManager
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            SNOOZE_ACTION -> {
                LogUtils.v("AlarmStateManager received SNOOZE_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.setSnoozeAlarm(context, it)
                    stateManager.stopService(context)
                }
            }

            DISMISS_ACTION -> {
                LogUtils.v("AlarmStateManager received DISMISS_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.dismissAlarm(context, it)
                    stateManager.stopService(context)
                }
            }

            SKIP_ALARM_ACTION -> {
                LogUtils.v("AlarmStateManager received SKIP_ALARM_ACTION")
                intent.getLongExtra("id", -1).let {
                    stateManager.skipAlarmSetPreEndNotification(context, it)
                }
            }

            CHANGE_STATE_ACTION -> {
                stateManager.handleIntent(context, intent)
            }

            else -> return
        }
    }
}