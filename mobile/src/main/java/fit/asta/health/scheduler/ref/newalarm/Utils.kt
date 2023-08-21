package fit.asta.health.scheduler.ref.newalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.alarms.AlarmNotifications
import fit.asta.health.scheduler.ref.alarms.AlarmStateManager
import fit.asta.health.scheduler.ref.provider.AlarmInstance

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
            AlarmInstance.createIntent(context, AlarmStateManager::class.java, instance.mAlarmId)
        intent.action = CHANGE_STATE_ACTION
        state?.let { intent.putExtra(ALARM_STATE_EXTRA, state) }
        return intent
    }

    fun updateNextAlarmInAlarmManager(context: Context, nextAlarm: AlarmInstance, state: Int?) {

        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val operation: PendingIntent = PendingIntent.getBroadcast(
            context,
            nextAlarm.hashCode(),
            createStateChangeIntent(context, nextAlarm, state),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        LogUtils.i("Setting upcoming AlarmClockInfo for alarm: " + nextAlarm.mId + "time " + nextAlarm.alarmTime)
        val alarmTime: Long = nextAlarm.alarmTime.timeInMillis

        // Create an intent that can be used to show or edit details of the next alarm.
        val viewIntent: PendingIntent =
            PendingIntent.getActivity(
                context, nextAlarm.hashCode(),
                AlarmNotifications.createViewAlarmIntent(context, nextAlarm),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val info = AlarmManager.AlarmClockInfo(alarmTime, viewIntent)
        alarmManager.setAlarmClock(info, operation)

    }
}