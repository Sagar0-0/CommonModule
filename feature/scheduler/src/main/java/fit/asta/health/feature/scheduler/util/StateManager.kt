package fit.asta.health.feature.scheduler.util

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import fit.asta.health.common.utils.Constants.CHANNEL_ID
import fit.asta.health.data.scheduler.db.AlarmDao
import fit.asta.health.data.scheduler.db.AlarmInstanceDao
import fit.asta.health.data.scheduler.db.entity.AlarmEntity
import fit.asta.health.data.scheduler.db.entity.AlarmInstance
import fit.asta.health.feature.scheduler.services.AlarmBroadcastReceiver
import fit.asta.health.feature.scheduler.services.AlarmService
import fit.asta.health.feature.scheduler.util.Utils.ALARM_STATE_EXTRA
import fit.asta.health.feature.scheduler.util.Utils.CURRENT_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.END_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.PRE_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.PRE_END_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.SKIP_ALARM_ACTION
import fit.asta.health.feature.scheduler.util.Utils.SNOOZE_CURRENT_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.SNOOZE_END_ALARM_STATE
import fit.asta.health.feature.scheduler.util.Utils.createStateChangeIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton
import fit.asta.health.resources.drawables.R as DrawR

@Singleton
class StateManager @Inject constructor(
    private val alarmDao: AlarmDao,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val alarmManager: AlarmManager
) {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    fun registerAlarm(
        context: Context,
        alarm: AlarmEntity,
        oldId: Long? = null,
        skipToday: Boolean = false
    ) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("register  ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val time: Calendar
        val state: Int
        if (alarm.interval.advancedReminder.status) {
            time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.PRE, skipToday)
            state = PRE_ALARM_STATE
        } else {
            time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.CURRENT, skipToday)
            state = CURRENT_ALARM_STATE
        }
        val instance = AlarmInstance(
            mId = (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
            mAlarmState = state,
            mAlarmId = alarm.alarmId,
            mLabel = alarm.info.name,
            mYear = time[Calendar.YEAR],
            mMonth = time[Calendar.MONTH],
            mDay = time[Calendar.DAY_OF_MONTH],
            mHour = time[Calendar.HOUR_OF_DAY],
            mMinute = time[Calendar.MINUTE]
        )
        oldId?.let {
            instance.mId = it
            cancelScheduledInstanceStateChange(context, instance)
        }
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        scheduleInstanceStateChange(
            context, instance.alarmTime, instance, state
        )
    }

    fun updateAlarm(context: Context, alarm: AlarmEntity, instanceOld: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("updateAlarm  ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val oldTime: Calendar = instanceOld.alarmTime
        val state: Int
        if (alarm.interval.statusEnd) {
            when (instanceOld.mAlarmState) {
                PRE_ALARM_STATE -> {
                    if (alarm.interval.advancedReminder.status) {
                        if (oldTime.timeInMillis < alarm.getCurrentAlarmTime(
                                currentTime,
                                AlarmEntity.State.PRE
                            ).timeInMillis
                        ) {
                            state = PRE_ALARM_STATE
                        }
                    } else registerAlarm(context, alarm, instanceOld.mId)
                }

                CURRENT_ALARM_STATE -> {
                    if (oldTime.timeInMillis < alarm.getCurrentAlarmTime(
                            currentTime,
                            AlarmEntity.State.CURRENT
                        ).timeInMillis
                    ) {
                        state = CURRENT_ALARM_STATE
                    } else registerAlarm(context, alarm, instanceOld.mId)
                }

                SNOOZE_CURRENT_ALARM_STATE -> {

                }

                PRE_END_ALARM_STATE -> {
                    if (alarm.interval.advancedReminder.status && alarm.interval.statusEnd) {
                        if (oldTime.timeInMillis < alarm.getCurrentAlarmTime(
                                currentTime,
                                AlarmEntity.State.PRE
                            ).timeInMillis
                        ) registerAlarm(context, alarm, instanceOld.mId)
                        else if (oldTime.timeInMillis < alarm.getCurrentAlarmTime(
                                currentTime,
                                AlarmEntity.State.PREEND
                            ).timeInMillis
                        ) {
                            state = PRE_END_ALARM_STATE
                        }
                    } else registerAlarm(context, alarm, instanceOld.mId)
                }

                END_ALARM_STATE -> {
                    if (oldTime.timeInMillis < alarm.getCurrentAlarmTime(
                            currentTime,
                            AlarmEntity.State.CURRENT
                        ).timeInMillis
                    ) {
                        state = CURRENT_ALARM_STATE
                    } else registerAlarm(context, alarm, instanceOld.mId)
                }

                SNOOZE_END_ALARM_STATE -> {

                }
            }
        } else {
            cancelScheduledInstanceStateChange(context, instanceOld)
        }
    }

    fun handleIntent(context: Context, intent: Intent) {
        LogUtils.v("handle intent $intent")
        val id: Long = intent.getLongExtra("id", -1)
        val alarmState: Int = intent.getIntExtra(ALARM_STATE_EXTRA, -1)
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(id)?.let { instance ->
                when (alarmState) {
                    PRE_ALARM_STATE -> {
                        // show notification
                        showNotification(context, instance)
                        setPreNotificationAlarm(context, instance)
                    }

                    CURRENT_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setCurrentAlarm(context, instance)
                        clearNotification(context, instance)
                        startAlarmService(context, id)
                    }

                    SNOOZE_CURRENT_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setCurrentAlarm(context, instance)
                        clearNotification(context, instance)
                        startAlarmService(context, id)
                    }

                    PRE_END_ALARM_STATE -> {
                        // show notification
                        showNotification(context, instance)
                        setPreNotificationEndAlarm(context, instance)
                    }

                    END_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setEndAlarm(context, instance)
                        clearNotification(context, instance)
                        startAlarmService(context, id)
                    }

                    SNOOZE_END_ALARM_STATE -> {
                        //start service
                        //clear notification
                        // setEndAlarm(context, instance)
                        clearNotification(context, instance)
                        startAlarmService(context, id)
                    }
                }
            }
        }
    }

    private fun missedAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmDao.getAlarm(instance.mAlarmId)?.let { alarm ->
                registerAlarm(context, alarm, instance.mId)
            }
        }
    }

    fun dismissAlarm(context: Context, id: Long) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("dismiss ${AlarmUtils.getFormattedTime(context, currentTime)}")
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(id)?.let { instance ->
                alarmDao.getAlarm(instance.mAlarmId)?.let { alarm ->
                    if (instance.mAlarmState == CURRENT_ALARM_STATE && alarm.interval.statusEnd) {
                        val time: Calendar
                        val state: Int
                        if (alarm.interval.advancedReminder.status) {
                            time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.PREEND)
                            state = PRE_END_ALARM_STATE
                        } else {
                            time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.END)
                            state = END_ALARM_STATE
                        }
                        instance.alarmTime = time
                        instance.mAlarmState = state
                        alarmInstanceDao.insertAndUpdate(instance)
                        scheduleInstanceStateChange(context, time, instance, state)

                    } else {
                        if (!alarm.daysOfWeek.isRepeating) {
                            cancelScheduledInstanceStateChange(context, instance)
                            alarmInstanceDao.delete(instance)
                            alarmDao.deleteAlarm(alarm)
                            return@launch
                        } else {
                            registerAlarm(context, alarm, instance.mId)
                        }
                    }

                }
            }
        }
    }

    fun setSnoozeAlarm(context: Context, id: Long) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("setSnooze  ${AlarmUtils.getFormattedTime(context, currentTime)}")
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(id)?.let { instance ->
                alarmDao.getAlarm(id)?.let { alarm ->
                    val state: Int =
                        when (instance.mAlarmState) {
                            CURRENT_ALARM_STATE -> SNOOZE_CURRENT_ALARM_STATE
                            SNOOZE_CURRENT_ALARM_STATE -> SNOOZE_CURRENT_ALARM_STATE
                            else -> SNOOZE_END_ALARM_STATE
                        }
                    val newAlarmTime = Calendar.getInstance()
                    newAlarmTime.add(Calendar.MINUTE, alarm.interval.snoozeTime)
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
    }


    private fun setPreNotificationEndAlarm(context: Context, instance: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v(
            "setPreNotificationEndAlarm ${
                AlarmUtils.getFormattedTime(
                    context,
                    currentTime
                )
            }"
        )
        scope.launch {
            alarmDao.getAlarm(instance.mAlarmId)?.let { alarm ->
                if (alarm.interval.statusEnd) {
                    val newAlarmTime = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.END)
                    instance.alarmTime = newAlarmTime
                    instance.mAlarmState = END_ALARM_STATE
                    alarmInstanceDao.insertAndUpdate(instance)
                    scheduleInstanceStateChange(context, newAlarmTime, instance, END_ALARM_STATE)
                } else {
                    registerAlarm(context, alarm, instance.mId)
                }
            }
        }
    }

    fun setCurrentAlarm(context: Context, instance: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("setCurrentAlarm ${AlarmUtils.getFormattedTime(context, currentTime)}")
        scope.launch {
            alarmDao.getAlarm(instance.mAlarmId)?.let { alarm ->
                val time: Calendar
                val state: Int
                if (alarm.interval.statusEnd) {
                    if (alarm.interval.advancedReminder.status) {
                        time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.PREEND)
                        state = PRE_END_ALARM_STATE
                    } else {
                        time = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.END)
                        state = END_ALARM_STATE
                    }
                    instance.alarmTime = time
                    instance.mAlarmState = state
                    alarmInstanceDao.insertAndUpdate(instance)
                    scheduleInstanceStateChange(context, time, instance, state)
                } else {
                    if (!alarm.daysOfWeek.isRepeating) {
                        cancelScheduledInstanceStateChange(context, instance)
                        alarmInstanceDao.delete(instance)
                        alarmDao.deleteAlarm(alarm)
                        return@launch
                    } else {
                        registerAlarm(context, alarm, instance.mId)
                    }
                }
            }
        }
    }

    private fun setPreNotificationAlarm(context: Context, instance: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("setPreNotificationAlarm ${AlarmUtils.getFormattedTime(context, currentTime)}")
        scope.launch {
            alarmDao.getAlarm(instance.mAlarmId)?.let { alarm ->
                val newAlarmTime = alarm.getNextAlarmTime(currentTime, AlarmEntity.State.CURRENT)
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

    fun skipAlarmSetPreEndNotification(context: Context, id: Long) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("skipAlarm ${AlarmUtils.getFormattedTime(context, currentTime)}")
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(id)?.let { instance ->
                alarmDao.getAlarm(id)?.let { alarm ->
                    cancelScheduledInstanceStateChange(context, instance)
                    if (instance.mAlarmState == END_ALARM_STATE) {
                        registerAlarm(context, alarm, instance.mId)
                    } else if (instance.mAlarmState == CURRENT_ALARM_STATE) {
                        if (alarm.interval.statusEnd && alarm.interval.advancedReminder.status) {
                            val newAlarmTime =
                                alarm.getNextAlarmTime(currentTime, AlarmEntity.State.PREEND)
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

    fun startAlarmService(context: Context, id: Long) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("startService ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val intentService = Intent(context.applicationContext, AlarmService::class.java)
        intentService.putExtra("id", id)
        ContextCompat.startForegroundService(context.applicationContext, intentService)
    }

    fun stopService(context: Context) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("stopService ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val intentService = Intent(context.applicationContext, AlarmService::class.java)
        context.stopService(intentService)
    }

    fun showNotification(context: Context, instance: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("show notification ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val skipIntent = Intent(context, AlarmBroadcastReceiver::class.java).apply {
            action = SKIP_ALARM_ACTION
            putExtra("id", instance.mAlarmId)
        }
        val pendingIntentStop = PendingIntent.getBroadcast(
            context,
            444,
            skipIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Pre Notification")
            .setContentText(instance.mLabel)
            .setSmallIcon(DrawR.drawable.ic_round_access_alarm_24)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .addAction(0, "Skip Alarm", pendingIntentStop)
            .setAutoCancel(true)

        notificationManager.notify(instance.hashCode(), notification.build())
    }

    fun clearNotification(context: Context, instance: AlarmInstance) {
        val currentTime: Calendar = Calendar.getInstance()
        LogUtils.v("clearNotification ${AlarmUtils.getFormattedTime(context, currentTime)}")
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(instance.hashCode())
    }

    fun deleteAlarm(context: Context, alarmItem: AlarmEntity) {
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(alarmItem.alarmId)?.let {
                cancelScheduledInstanceStateChange(context, it)
                alarmInstanceDao.delete(it)
            }
        }
    }

    fun skipAlarmTodayOnly(context: Context, alarmItem: AlarmEntity) {
        scope.launch {
            alarmInstanceDao.getInstancesByAlarmId(alarmItem.alarmId)?.let { instance ->
                registerAlarm(context, alarmItem, instance.mId, true)
            }
        }
    }
}

