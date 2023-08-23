package fit.asta.health.scheduler.ref.newalarm

import android.content.Context
import android.content.Intent
import fit.asta.health.scheduler.data.db.entity.AlarmInstance
import fit.asta.health.scheduler.services.AlarmBroadcastReceiver

object Utils {
    const val PRE_ALARM_STATE = 1
    const val CURRENT_ALARM_STATE = 2
    const val SNOOZE_CURRENT_ALARM_STATE = 3
    const val PRE_END_ALARM_STATE = 4
    const val END_ALARM_STATE = 5
    const val SNOOZE_END_ALARM_STATE = 6
    const val MISSED_ALARM_STATE = 7

    val CHANGE_STATE_ACTION = "change_state"
    val SNOOZE_ACTION = "snooze_state"
    val DISMISS_ACTION = "dismiss_state"
    val SKIP_ALARM_ACTION = "skip_alarm_state"
    val ALARM_STATE_EXTRA = "intent.extra.alarm.state"
    fun createStateChangeIntent(
        context: Context?,
        instance: AlarmInstance,
        state: Int?
    ): Intent {

        val intent: Intent =
            AlarmInstance.createIntent(
                context,
                AlarmBroadcastReceiver::class.java,
                instance.mAlarmId
            )
        intent.action = CHANGE_STATE_ACTION
        state?.let { intent.putExtra(ALARM_STATE_EXTRA, state) }
        return intent
    }

}