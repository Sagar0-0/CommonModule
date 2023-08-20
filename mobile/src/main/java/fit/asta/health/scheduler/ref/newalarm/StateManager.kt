package fit.asta.health.scheduler.ref.newalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import fit.asta.health.scheduler.ref.AlarmUtils
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.Utils
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.db.AlarmRefDao
import fit.asta.health.scheduler.ref.newalarm.Utils.CURRENT_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.END_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.PRE_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.PRE_END_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.SNOOZE_CURRENT_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.SNOOZE_END_ALARM_STATE
import fit.asta.health.scheduler.ref.newalarm.Utils.createStateChangeIntent
import fit.asta.health.scheduler.ref.provider.Alarm
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateManager @Inject constructor(
    private val alarmRefDao: AlarmRefDao,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val alarmManager: AlarmManager
) {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val currentTime: Calendar = Calendar.getInstance()

    fun registerAlarm(context: Context, alarm: Alarm, oldId: Long? = null) {
        val time: Calendar
        val state: Int
        if (alarm.preEnabled) {
            time = alarm.getNextAlarmTime(currentTime, Alarm.State.PRE)
            state = PRE_ALARM_STATE
        } else {
            time = alarm.getNextAlarmTime(currentTime, Alarm.State.CURRENT)
            state = CURRENT_ALARM_STATE
        }
        val instance = AlarmInstance(
            mId = (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
            mLabel = alarm.label,
            mVibrate = alarm.vibrate,
            mRingtone = alarm.alert.toString(),
            mAlarmState = state,
            mAlarmId = alarm.id,
            mYear = time[Calendar.YEAR],
            mMonth = time[Calendar.MONTH],
            mDay = time[Calendar.DAY_OF_MONTH],
            mHour = time[Calendar.HOUR_OF_DAY],
            mMinute = time[Calendar.MINUTE]
        )
        oldId?.let { instance.mId = it }
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        scheduleInstanceStateChange(
            context, instance.alarmTime, instance, state
        )
    }

    fun handleIntent(context: Context, intent: Intent) {
        LogUtils.v("AlarmStateManager received intent $intent")
        val id: Long = intent.getLongExtra("id", -1)
        val alarmState: Int = intent.getIntExtra(Utils.ALARM_STATE_EXTRA, -1)
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(id)?.let { instance ->
                when (alarmState) {
                    PRE_ALARM_STATE -> {
                        // show notification
                        setPreNotificationAlarm(context, instance)
                    }

                    CURRENT_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setCurrentAlarm(context, instance)
                    }

                    SNOOZE_CURRENT_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setCurrentAlarm(context, instance)
                    }

                    PRE_END_ALARM_STATE -> {
                        // show notification
                        setPreNotificationEndAlarm(context, instance)
                    }

                    END_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setEndAlarm(context, instance)
                    }

                    SNOOZE_END_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setEndAlarm(context, instance)
                    }
                }
            }
        }
    }

    private fun missedAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                registerAlarm(context, alarm, instance.mId)
            }
        }
    }

    private fun dismissAlarm(context: Context, instance: AlarmInstance) {
        setCurrentAlarm(context, instance)
    }

    private fun setSnoozeAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                val state: Int =
                    when (instance.mAlarmState) {
                        CURRENT_ALARM_STATE -> SNOOZE_CURRENT_ALARM_STATE
                        SNOOZE_CURRENT_ALARM_STATE -> SNOOZE_CURRENT_ALARM_STATE
                        else -> SNOOZE_END_ALARM_STATE
                    }
                val newAlarmTime = Calendar.getInstance()
                newAlarmTime.add(Calendar.MINUTE, alarm.snooze)
                instance.alarmTime = newAlarmTime
                instance.mAlarmState = state
                alarmInstanceDao.insertAndUpdate(instance)
                scheduleInstanceStateChange(
                    context,
                    newAlarmTime,
                    instance,
                    state
                )
            }
        }
    }


    private fun setPreNotificationEndAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                val newAlarmTime = alarm.getNextAlarmTime(currentTime, Alarm.State.END)
                instance.alarmTime = newAlarmTime
                instance.mAlarmState = END_ALARM_STATE
                alarmInstanceDao.insertAndUpdate(instance)
                scheduleInstanceStateChange(context, newAlarmTime, instance, END_ALARM_STATE)
            }
        }
    }

    fun setCurrentAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                val time: Calendar
                val state: Int
                if (alarm.endEnabled) {
                    if (alarm.preEnabled) {
                        time = alarm.getNextAlarmTime(currentTime, Alarm.State.PREEND)
                        state = PRE_END_ALARM_STATE
                    } else {
                        time = alarm.getNextAlarmTime(currentTime, Alarm.State.END)
                        state = END_ALARM_STATE
                    }
                    instance.alarmTime = time
                    instance.mAlarmState = state
                    alarmInstanceDao.insertAndUpdate(instance)
                    scheduleInstanceStateChange(context, time, instance, state)
                } else {
                    if (alarm.deleteAfterUse) {
                        cancelScheduledInstanceStateChange(context, instance)
                        alarmInstanceDao.delete(instance)
                        alarmRefDao.delete(alarm)
                        return@launch
                    } else {
                        registerAlarm(context, alarm, instance.mId)
                    }
                }
            }
        }
    }

    private fun setPreNotificationAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                val newAlarmTime = alarm.getNextAlarmTime(currentTime, Alarm.State.CURRENT)
                instance.alarmTime = newAlarmTime
                instance.mAlarmState = CURRENT_ALARM_STATE
                alarmInstanceDao.insertAndUpdate(instance)
                scheduleInstanceStateChange(
                    context,
                    newAlarmTime,
                    instance,
                    CURRENT_ALARM_STATE
                )
            }
        }
    }

    fun skipAlarmSetPreEndNotification(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                if (instance.mAlarmState == END_ALARM_STATE) {
                    registerAlarm(context, alarm, instance.mId)
                } else if (instance.mAlarmState == CURRENT_ALARM_STATE) {
                    if (alarm.endEnabled) {
                        cancelScheduledInstanceStateChange(context, instance)
                        val newAlarmTime =
                            alarm.getNextAlarmTime(currentTime, Alarm.State.PREEND)
                        instance.alarmTime = newAlarmTime
                        instance.mAlarmState = PRE_END_ALARM_STATE
                        alarmInstanceDao.insertAndUpdate(instance)
                        scheduleInstanceStateChange(
                            context,
                            newAlarmTime,
                            instance,
                            PRE_END_ALARM_STATE
                        )
                    } else {
                        registerAlarm(context, alarm, instance.mId)
                    }
                }

            }
        }
    }

    private fun scheduleInstanceStateChange(
        context: Context,
        time: Calendar,
        instance: AlarmInstance,
        newState: Int
    ) {
        val timeInMillis = time.timeInMillis
        LogUtils.i(
            "Scheduling state change %d to instance %d at %s (%d)", newState,
            instance.mId, AlarmUtils.getFormattedTime(context, time), timeInMillis
        )
        val stateChangeIntent: Intent = createStateChangeIntent(context, instance, newState)
        // Treat alarm state change as high priority, use foreground broadcasts
        stateChangeIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(
                context, instance.hashCode(),
                stateChangeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        val info = AlarmManager.AlarmClockInfo(timeInMillis, pendingIntent)
        alarmManager.setAlarmClock(info, pendingIntent)
    }

    fun cancelScheduledInstanceStateChange(context: Context, instance: AlarmInstance) {
        LogUtils.v("Canceling instance " + instance.mId + " timers")

        val pendingIntent: PendingIntent? =
            PendingIntent.getBroadcast(
                context, instance.hashCode(),
                createStateChangeIntent(context, instance, null),
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

        pendingIntent?.let {
            val am: AlarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(it)
            it.cancel()
        }
    }
}

