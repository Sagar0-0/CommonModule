package fit.asta.health.scheduler.ref.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import fit.asta.health.scheduler.ref.AlarmUtils
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.Utils
import fit.asta.health.scheduler.ref.Utils.ALARM_DISMISS_TAG
import fit.asta.health.scheduler.ref.Utils.ALARM_GLOBAL_ID_EXTRA
import fit.asta.health.scheduler.ref.Utils.ALARM_SNOOZE_TAG
import fit.asta.health.scheduler.ref.Utils.ALARM_STATE_EXTRA
import fit.asta.health.scheduler.ref.Utils.CHANGE_STATE_ACTION
import fit.asta.health.scheduler.ref.Utils.INDICATOR_ACTION
import fit.asta.health.scheduler.ref.Utils.SHOW_AND_DISMISS_ALARM_ACTION
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.db.AlarmRefDao
import fit.asta.health.scheduler.ref.provider.Alarm
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import fit.asta.health.scheduler.ref.provider.ClockContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AlarmDataManager @Inject constructor(
    private val alarmRefDao: AlarmRefDao,
    private val alarmInstanceDao: AlarmInstanceDao
) {

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)


    // Buffer time in seconds to fire alarm instead of marking it missed.
    val ALARM_FIRE_BUFFER = 15

    // A factory for the current time; can be mocked for testing purposes.
    private var sCurrentTimeFactory: CurrentTimeFactory? = null

    // Schedules alarm state transitions; can be mocked for testing purposes.
    private var sStateChangeScheduler: StateChangeScheduler =
        AlarmManagerStateChangeScheduler()

    private val currentTime: Calendar
        get() = (if (sCurrentTimeFactory == null) {
            Calendar.getInstance()
        } else {
            sCurrentTimeFactory!!.currentTime
        })

    fun setCurrentTimeFactory(currentTimeFactory: CurrentTimeFactory?) {
        sCurrentTimeFactory = currentTimeFactory
    }

    fun setStateChangeScheduler(stateChangeScheduler: StateChangeScheduler?) {
        sStateChangeScheduler = stateChangeScheduler ?: AlarmManagerStateChangeScheduler()
    }


    /**
     * Returns an alarm instance of an alarm that's going to fire next.
     *
     * @param context application context
     * @return an alarm instance that will fire earliest relative to current time.
     */


    /**
     * Used in L and later devices where "next alarm" is stored in the Alarm Manager.
     */
    private fun updateNextAlarmInAlarmManager(context: Context, nextAlarm: AlarmInstance?) {
        // Sets a surrogate alarm with alarm manager that provides the AlarmClockInfo for the
        // alarm that is going to fire next. The operation is constructed such that it is
        // ignored by AlarmStateManager.

        val alarmManager: AlarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val operation: PendingIntent? = PendingIntent.getBroadcast(
            context,
            0 /* requestCode */,
            createIndicatorIntent(context),
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (nextAlarm != null) {
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
//            alarmManager.setAlarmClock(info, operation!!)
            Utils.updateNextAlarm(alarmManager, info, operation)
        } else if (operation != null) {
            LogUtils.i("Canceling upcoming AlarmClockInfo")
            alarmManager.cancel(operation)
        }
    }

    /**
     * Used by dismissed and missed states, to update parent alarm. This will either
     * disable, delete or reschedule parent alarm.
     *
     * @param context application context
     * @param instance to update parent for
     */
    private fun updateParentAlarm(context: Context, instance: AlarmInstance) {
        scope.launch {
            alarmRefDao.getAlarmById(instance.mAlarmId)?.let { alarm ->
                if (!alarm.daysOfWeek.isRepeating) {
                    if (alarm.deleteAfterUse) {
                        LogUtils.i("Deleting parent alarm: " + alarm.id)
                        alarmRefDao.delete(alarm)
                    } else {
                        LogUtils.i("Disabling parent alarm: " + alarm.id)
                        alarm.enabled = false
                        alarmRefDao.insertAndUpdate(alarm)
                    }
                } else {
                    // Schedule the next repeating instance which may be before the current instance if
                    // a time jump has occurred. Otherwise, if the current instance is the next instance
                    // and has already been fired, schedule the subsequent instance.
//                    var nextRepeatedInstance = alarm.createInstanceAfter(currentTime)
//                    if (instance.mAlarmState > ClockContract.InstancesColumns.FIRED_STATE &&
//                        nextRepeatedInstance.alarmTime == instance.alarmTime
//                    ) {
//                        nextRepeatedInstance = alarm.createInstanceAfter(instance.alarmTime)
//                    }

//                    LogUtils.i(
//                        "Creating new instance for repeating alarm " + alarm.id +
//                                " at " +
//                                AlarmUtils.getFormattedTime(
//                                    context,
//                                    nextRepeatedInstance.alarmTime
//                                )
//                    )
//                    alarmInstanceDao.insertAndUpdate(nextRepeatedInstance)
//                    registerInstance(context, nextRepeatedInstance, true)
                }
            }
        }
    }


    /**
     * Schedule alarm instance state changes with [AlarmManager].
     *
     * @param ctx application context
     * @param time to trigger state change
     * @param instance to change state to
     * @param newState to change to
     */
    private fun scheduleInstanceStateChange(
        ctx: Context,
        time: Calendar,
        instance: AlarmInstance,
        newState: Int
    ) {
        sStateChangeScheduler.scheduleInstanceStateChange(ctx, time, instance, newState)
    }

    /**
     * Cancel all [AlarmManager] timers for instance.
     *
     * @param ctx application context
     * @param instance to disable all [AlarmManager] timers
     */
    private fun cancelScheduledInstanceStateChange(ctx: Context, instance: AlarmInstance) {
        sStateChangeScheduler.cancelScheduledInstanceStateChange(ctx, instance)
    }

    /**
     * This will set the alarm instance to the SILENT_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    private fun setSilentState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting silent state to instance " + instance.mId)

        // Update alarm in db
        instance.mAlarmState = ClockContract.InstancesColumns.SILENT_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.clearNotification(context, instance)
//        scheduleInstanceStateChange(
//            context, instance.lowNotificationTime,
//            instance, ClockContract.InstancesColumns.LOW_NOTIFICATION_STATE
//        )
    }

    /**
     * This will set the alarm instance to the LOW_NOTIFICATION_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    private fun setLowNotificationState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting low notification state to instance " + instance.mId)

        // Update alarm state in db

        instance.mAlarmState = ClockContract.InstancesColumns.LOW_NOTIFICATION_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.showLowPriorityNotification(context, instance)
//        scheduleInstanceStateChange(
//            context, instance.highNotificationTime,
//            instance, ClockContract.InstancesColumns.HIGH_NOTIFICATION_STATE
//        )
    }

    /**
     * This will set the alarm instance to the HIDE_NOTIFICATION_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    private fun setHideNotificationState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting hide notification state to instance " + instance.mId)

        // Update alarm state in db

        instance.mAlarmState = ClockContract.InstancesColumns.HIDE_NOTIFICATION_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.clearNotification(context, instance)
//        scheduleInstanceStateChange(
//            context, instance.highNotificationTime,
//            instance, ClockContract.InstancesColumns.HIGH_NOTIFICATION_STATE
//        )
    }

    /**
     * This will set the alarm instance to the HIGH_NOTIFICATION_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    private fun setHighNotificationState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting high notification state to instance " + instance.mId)

        // Update alarm state in db

        instance.mAlarmState = ClockContract.InstancesColumns.HIGH_NOTIFICATION_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.showHighPriorityNotification(context, instance)
        scheduleInstanceStateChange(
            context, instance.alarmTime,
            instance, ClockContract.InstancesColumns.FIRED_STATE
        )
    }

    /**
     * This will set the alarm instance to the FIRED_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    private fun setFiredState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting fire state to instance " + instance.mId)

        // Update alarm state in db

        instance.mAlarmState = ClockContract.InstancesColumns.FIRED_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        instance.mAlarmId.let { alarmId ->
            // if the time changed *backward* and pushed an instance from missed back to fired,
            // remove any other scheduled instances that may exist
            scope.launch {
    //                alarmInstanceDao.getInstancesByAlarmId(alarmId).forEach {
    //                    if (it.mId != instance.mId) {
    //                        unregisterInstance(context, it)
    //                        alarmInstanceDao.delete(it)
    //                    }
    //                }
            }
        }

//            Events.sendAlarmEvent(R.string.action_fire, 0)

        val timeout: Calendar = instance.timeout
        timeout?.let {
            scheduleInstanceStateChange(
                context,
                it,
                instance,
                ClockContract.InstancesColumns.MISSED_STATE
            )
        }

        // Instance not valid anymore, so find next alarm that will fire and notify system
        updateNextAlarm(context)
    }

    /**
     * This will set the alarm instance to the SNOOZE_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */

    fun setSnoozeState(
        context: Context,
        instance: AlarmInstance,
        showToast: Boolean
    ) {
        // Stop alarm if this instance is firing it
        AlarmService.stopAlarm(context, instance)

        // Calculate the new snooze alarm time
        val snoozeMinutes = 5
        val newAlarmTime = Calendar.getInstance()
        newAlarmTime.add(Calendar.MINUTE, snoozeMinutes)

        // Update alarm state and new alarm time in db.
        LogUtils.i(
            "Setting snoozed state to instance " + instance.mId + " for " +
                    AlarmUtils.getFormattedTime(context, newAlarmTime)
        )
        instance.alarmTime = newAlarmTime
        instance.mAlarmState = ClockContract.InstancesColumns.SNOOZE_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.showSnoozeNotification(context, instance)
        scheduleInstanceStateChange(
            context, instance.alarmTime,
            instance, ClockContract.InstancesColumns.FIRED_STATE
        )

        // Display the snooze minutes in a toast.
        if (showToast) {
            val mainHandler = Handler(context.mainLooper)
            val myRunnable = Runnable {
                Toast.makeText(context, "$snoozeMinutes", Toast.LENGTH_LONG).show()
            }
            mainHandler.post(myRunnable)
        }

        // Instance time changed, so find next alarm that will fire and notify system
        updateNextAlarm(context)
    }

    /**
     * This will set the alarm instance to the MISSED_STATE and update
     * the application notifications and schedule any state changes that need
     * to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */
    fun setMissedState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting missed state to instance " + instance.mId)
        // Stop alarm if this instance is firing it
        AlarmService.stopAlarm(context, instance)

        // Check parent if it needs to reschedule, disable or delete itself
        if (instance.mAlarmId != null) {
            updateParentAlarm(context, instance)
        }

        // Update alarm state

        instance.mAlarmState = ClockContract.InstancesColumns.MISSED_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.showMissedNotification(context, instance)
//        scheduleInstanceStateChange(
//            context, instance.missedTimeToLive,
//            instance, ClockContract.InstancesColumns.DISMISSED_STATE
//        )

        // Instance is not valid anymore, so find next alarm that will fire and notify system
        updateNextAlarm(context)
    }

    /**
     * This will set the alarm instance to the PREDISMISSED_STATE and schedule an instance state
     * change to DISMISSED_STATE at the regularly scheduled firing time.
     *
     * @param context application context
     * @param instance to set state to
     */

    fun setPreDismissState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting predismissed state to instance " + instance.mId)

        // Update alarm in db

        instance.mAlarmState = ClockContract.InstancesColumns.PREDISMISSED_STATE
        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }

        // Setup instance notification and scheduling timers
        AlarmNotifications.clearNotification(context, instance)
        scheduleInstanceStateChange(
            context, instance.alarmTime, instance,
            ClockContract.InstancesColumns.DISMISSED_STATE
        )

        // Check parent if it needs to reschedule, disable or delete itself
        if (instance.mAlarmId != null) {
            updateParentAlarm(context, instance)
        }

        updateNextAlarm(context)
    }

    /**
     * This just sets the alarm instance to DISMISSED_STATE.
     */
    private fun setDismissState(context: Context, instance: AlarmInstance) {
        LogUtils.i("Setting dismissed state to instance " + instance.mId)
        instance.mAlarmState = ClockContract.InstancesColumns.DISMISSED_STATE

        scope.launch { alarmInstanceDao.insertAndUpdate(instance) }
    }

    /**
     * This will delete the alarm instance, update the application notifications, and schedule
     * any state changes that need to occur in the future.
     *
     * @param context application context
     * @param instance to set state to
     */

    fun deleteInstanceAndUpdateParent(context: Context, instance: AlarmInstance) {
        LogUtils.i("Deleting instance " + instance.mId + " and updating parent alarm.")

        // Remove all other timers and notifications associated to it
        unregisterInstance(context, instance)

        // Check parent if it needs to reschedule, disable or delete itself
        if (instance.mAlarmId != null) {
            updateParentAlarm(context, instance)
        }

        // Delete instance as it is not needed anymore
        scope.launch { alarmInstanceDao.delete(instance) }

        // Instance is not valid anymore, so find next alarm that will fire and notify system
        updateNextAlarm(context)
    }

    /**
     * This will set the instance state to DISMISSED_STATE and remove its notifications and
     * alarm timers.
     *
     * @param context application context
     * @param instance to unregister
     */
    fun unregisterInstance(context: Context, instance: AlarmInstance) {
        LogUtils.i("Unregistering instance " + instance.mId)
        // Stop alarm if this instance is firing it
        AlarmService.stopAlarm(context, instance)
        AlarmNotifications.clearNotification(context, instance)
        cancelScheduledInstanceStateChange(context, instance)
        setDismissState(context, instance)
    }

    /**
     * This registers the AlarmInstance to the state manager. This will look at the instance
     * and choose the most appropriate state to put it in. This is primarily used by new
     * alarms, but it can also be called when the system time changes.
     *
     * Most state changes are handled by the states themselves, but during major time changes we
     * have to correct the alarm instance state. This means we have to handle special cases as
     * describe below:
     *
     *
     *  * Make sure all dismissed alarms are never re-activated
     *  * Make sure pre-dismissed alarms stay predismissed
     *  * Make sure firing alarms stayed fired unless they should be auto-silenced
     *  * Missed instance that have parents should be re-enabled if we went back in time
     *  * If alarm was SNOOZED, then show the notification but don't update time
     *  * If low priority notification was hidden, then make sure it stays hidden
     *
     *
     * If none of these special case are found, then we just check the time and see what is the
     * proper state for the instance.
     *
     * @param context application context
     * @param instance to register
     */

    fun registerInstance(
        context: Context,
        instance: AlarmInstance,
        updateNextAlarm: Boolean
    ) {
        LogUtils.i("Registering instance: " + instance.mId)
        val currentTime = currentTime
        val alarmTime: Calendar = instance.alarmTime
        val timeoutTime: Calendar = instance.timeout
//        val lowNotificationTime: Calendar = instance.lowNotificationTime
//        val highNotificationTime: Calendar = instance.highNotificationTime
//        val missedTTL: Calendar = instance.missedTimeToLive

        // Handle special use cases here
        if (instance.mAlarmState == ClockContract.InstancesColumns.DISMISSED_STATE) {
            // This should never happen, but add a quick check here
            LogUtils.e("Alarm Instance is dismissed, but never deleted")
            deleteInstanceAndUpdateParent(context, instance)
            return
        } else if (instance.mAlarmState == ClockContract.InstancesColumns.FIRED_STATE) {
            // Keep alarm firing, unless it should be timed out
            val hasTimeout = timeoutTime != null && currentTime.after(timeoutTime)
            if (!hasTimeout) {
                setFiredState(context, instance)
                return
            }
        } else if (instance.mAlarmState == ClockContract.InstancesColumns.MISSED_STATE) {
            if (currentTime.before(alarmTime)) {
                if (instance.mAlarmId == null) {
                    LogUtils.i("Cannot restore missed instance for one-time alarm")
                    // This instance parent got deleted (ie. deleteAfterUse), so
                    // we should not re-activate it.-
                    deleteInstanceAndUpdateParent(context, instance)
                    return
                }

                // TODO: This will re-activate missed snoozed alarms, but will
                // use our normal notifications. This is not ideal, but very rare use-case.
                // We should look into fixing this in the future.

                // Make sure we re-enable the parent alarm of the instance
                // because it will get activated by by the below code
                scope.launch {
                    alarmRefDao.getAlarmById(instance.mAlarmId)?.let {
                        it.enabled = true
                        alarmRefDao.insertAndUpdate(it)
                    }
                }
            }
        } else if (instance.mAlarmState == ClockContract.InstancesColumns.PREDISMISSED_STATE) {
            if (currentTime.before(alarmTime)) {
                setPreDismissState(context, instance)
            } else {
                deleteInstanceAndUpdateParent(context, instance)
            }
            return
        }

        // Fix states that are time sensitive
//        if (currentTime.after(missedTTL)) {
//            // Alarm is so old, just dismiss it
//            deleteInstanceAndUpdateParent(context, instance)
//        } else if (currentTime.after(alarmTime)) {
//            // There is a chance that the TIME_SET occurred right when the alarm should go off,
//            // so we need to add a check to see if we should fire the alarm instead of marking
//            // it missed.
//            val alarmBuffer = Calendar.getInstance()
//            alarmBuffer.time = alarmTime.time
//            alarmBuffer.add(Calendar.SECOND, ALARM_FIRE_BUFFER)
//            if (currentTime.before(alarmBuffer)) {
//                setFiredState(context, instance)
//            } else {
//                setMissedState(context, instance)
//            }
//        }
//        else if (instance.mAlarmState == ClockContract.InstancesColumns.SNOOZE_STATE) {
//            // We only want to display snooze notification and not update the time,
//            // so handle showing the notification directly
//            AlarmNotifications.showSnoozeNotification(context, instance)
//            scheduleInstanceStateChange(
//                context, instance.alarmTime,
//                instance, ClockContract.InstancesColumns.FIRED_STATE
//            )
//        } else if (currentTime.after(highNotificationTime)) {
//            setHighNotificationState(context, instance)
//        } else if (currentTime.after(lowNotificationTime)) {
//            // Only show low notification if it wasn't hidden in the past
//            if (instance.mAlarmState == ClockContract.InstancesColumns.HIDE_NOTIFICATION_STATE) {
//                setHideNotificationState(context, instance)
//            } else {
//                setLowNotificationState(context, instance)
//            }
//        } else {
//            // Alarm is still active, so initialize as a silent alarm
//            setSilentState(context, instance)
//        }

        // The caller prefers to handle updateNextAlarm for optimization
        if (updateNextAlarm) {
            updateNextAlarm(context)
        }
    }

    /**
     * This will delete and unregister all instances associated with alarmId, without affect
     * the alarm itself. This should be used whenever modifying or deleting an alarm.
     *
     * @param context application context
     * @param alarmId to find instances to delete.
     */
//    fun deleteAllInstances(context: Context, alarmId: Long) {
//        LogUtils.i("Deleting all instances of alarm: $alarmId")
//        scope.launch {
//            alarmInstanceDao.getInstancesByAlarmId(alarmId).forEach { instance ->
//                unregisterInstance(context, instance)
//                alarmInstanceDao.delete(instance)
//            }
//        }
//        updateNextAlarm(context)
//    }

    /**
     * Delete and unregister all instances unless they are snoozed. This is used whenever an
     * alarm is modified superficially (label, vibrate, or ringtone change).
     */
//    fun deleteNonSnoozeInstances(context: Context, alarmId: Long) {
//        LogUtils.i("Deleting all non-snooze instances of alarm: $alarmId")
//
//        scope.launch {
//            alarmInstanceDao.getInstancesByAlarmId(alarmId).forEach { instance ->
//                if (instance.mAlarmState != ClockContract.InstancesColumns.SNOOZE_STATE) {
//                    unregisterInstance(context, instance)
//                    alarmInstanceDao.delete(instance)
//                }
//            }
//        }
//        updateNextAlarm(context)
//    }

    /**
     * Fix and update all alarm instance when a time change event occurs.
     *
     * @param context application context
     */
    fun fixAlarmInstances(context: Context) {
        LogUtils.i("Fixing alarm instances")
        // Register all instances after major time changes or when phone restarts

        val currentTime = currentTime

        // Sort the instances in reverse chronological order so that later instances are fixed
        // or deleted before re-scheduling prior instances (which may re-create or update the
        // later instances).
        scope.launch {
            alarmInstanceDao.getInstancesSortedByTimeDescending().forEach { instance ->
                val alarm = alarmRefDao.getAlarmById(instance.mAlarmId)
                if (alarm == null) {
                    unregisterInstance(context, instance)
                    alarmInstanceDao.delete(instance)
                    LogUtils.e(
                        "Found instance without matching alarm; deleting instance %s",
                        instance
                    )
                } else {
                    val priorAlarmTime = alarm.getPreviousAlarmTime(instance.alarmTime)
//                    val missedTTLTime: Calendar = instance.missedTimeToLive
//                    if (currentTime.before(priorAlarmTime) || currentTime.after(missedTTLTime)) {
//                        val oldAlarmTime: Calendar = instance.alarmTime
//                        val newAlarmTime = alarm.getNextAlarmTime(currentTime)
//                        val oldTime: CharSequence =
//                            DateFormat.format("MM/dd/yyyy hh:mm a", oldAlarmTime)
//                        val newTime: CharSequence =
//                            DateFormat.format("MM/dd/yyyy hh:mm a", newAlarmTime)
//                        LogUtils.i(
//                            "A time change has caused an existing alarm scheduled" +
//                                    " to fire at %s to be replaced by a new alarm scheduled to fire at %s",
//                            oldTime, newTime
//                        )
//
//                        // The time change is so dramatic the AlarmInstance doesn't make any sense;
//                        // remove it and schedule the new appropriate instance.
//                        deleteInstanceAndUpdateParent(context, instance)
//                    } else {
//                        registerInstance(context, instance, false /* updateNextAlarm */)
//                    }
                }
            }
        }
//        val instances = AlarmInstance.getInstances(
//            null /* selection */
//        )
//        instances.sortWith { lhs, rhs -> rhs.alarmTime.compareTo(lhs.alarmTime) }
//        for (instance in instances) { }

        updateNextAlarm(context)
    }

    /**
     * Utility method to set alarm instance state via constants.
     *
     * @param context application context
     * @param instance to change state on
     * @param state to change to
     */
    private fun setAlarmState(context: Context, instance: AlarmInstance?, state: Int) {
        if (instance == null) {
            LogUtils.e("Null alarm instance while setting state to %d", state)
            return
        }
        when (state) {
            ClockContract.InstancesColumns.SILENT_STATE -> setSilentState(context, instance)
            ClockContract.InstancesColumns.LOW_NOTIFICATION_STATE -> {
                setLowNotificationState(context, instance)
            }

            ClockContract.InstancesColumns.HIDE_NOTIFICATION_STATE -> {
                setHideNotificationState(context, instance)
            }

            ClockContract.InstancesColumns.HIGH_NOTIFICATION_STATE -> {
                setHighNotificationState(context, instance)
            }

            ClockContract.InstancesColumns.FIRED_STATE -> setFiredState(context, instance)
            ClockContract.InstancesColumns.SNOOZE_STATE -> {
                setSnoozeState(context, instance, true /* showToast */)
            }

            ClockContract.InstancesColumns.MISSED_STATE -> setMissedState(context, instance)
            ClockContract.InstancesColumns.PREDISMISSED_STATE -> setPreDismissState(
                context,
                instance
            )

            ClockContract.InstancesColumns.DISMISSED_STATE -> deleteInstanceAndUpdateParent(
                context,
                instance
            )

            else -> LogUtils.e("Trying to change to unknown alarm state: $state")
        }
    }

    fun handleIntent(context: Context, intent: Intent) {
        val action: String? = intent.action
        LogUtils.v("AlarmStateManager received intent $intent")
        if (CHANGE_STATE_ACTION == action) {
            val id: Long = intent.getLongExtra("id", -1)
            scope.launch {
                alarmInstanceDao.getInstance(id)?.let { instance ->
                    val globalId = -1
                    val intentId: Int = intent.getIntExtra(ALARM_GLOBAL_ID_EXTRA, -1)
                    val alarmState: Int = intent.getIntExtra(ALARM_STATE_EXTRA, -1)
                    if (intentId != globalId) {
                        LogUtils.i(
                            "IntentId: " + intentId + " GlobalId: " + globalId +
                                    " AlarmState: " + alarmState
                        )
                        // Allows dismiss/snooze requests to go through
                        if (!intent.hasCategory(ALARM_DISMISS_TAG) && !intent.hasCategory(
                                ALARM_SNOOZE_TAG
                            )
                        ) {
                            LogUtils.i("Ignoring old Intent")
                            return@launch
                        }
                    }
                    if (alarmState >= 0) {
                        setAlarmState(context, instance, alarmState)
                    } else {
                        registerInstance(context, instance, true)
                    }
                }
            }
        } else if (SHOW_AND_DISMISS_ALARM_ACTION == action) {
            val id: Long = intent.getLongExtra("id", -1)
            scope.launch {
                val instance: AlarmInstance? = alarmInstanceDao.getInstance(id)
                if (instance == null) {
                    LogUtils.e("Null alarminstance for SHOW_AND_DISMISS")
                    // dismiss the notification
                    val id1: Int =
                        intent.getIntExtra(AlarmNotifications.EXTRA_NOTIFICATION_ID, -1)
                    if (id1 != -1) {
                        NotificationManagerCompat.from(context).cancel(id1)
                    }
                    return@launch
                } else {
                    val alarmId = instance.mAlarmId
                    val viewAlarmIntent: Intent =
                        Alarm.createIntent(
                            context,
                            AlarmActivity::class.java,
                            alarmId
                        ) //Deskclock activity
//                            .putExtra(AlarmClockFragment.SCROLL_TO_ALARM_INTENT_EXTRA, alarmId)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

//                    Open DeskClock which is now positioned on the alarms tab.
                    context.startActivity(viewAlarmIntent)
                    deleteInstanceAndUpdateParent(context, instance)
                }
            }
        }
    }

    /**
     * Creates an intent that can be used to set an AlarmManager alarm to set the next alarm
     * indicators.
     */
    private fun createIndicatorIntent(context: Context?): Intent {
        return Intent(context, AlarmStateManager::class.java).setAction(INDICATOR_ACTION)
    }

    /**
     * Update the next alarm stored in framework. This value is also displayed in digital
     * widgets and the clock tab in this app.
     */
    private fun updateNextAlarm(context: Context) {
        scope.launch {
            getNextFiringAlarm()?.let { nextAlarm ->
                LogUtils.d("next alarm time" + nextAlarm.alarmTime + "" + nextAlarm.mId + nextAlarm.mDay + nextAlarm.mHour)
                updateNextAlarmInAlarmManager(context, nextAlarm)
            }
        }
    }

    private suspend fun getNextFiringAlarm(): AlarmInstance? {
        return alarmInstanceDao.getEarliestFiredAlarm()
    }
}

/**
 * Abstract away how the current time is computed. If no implementation of this interface is
 * given the default is to return [Calendar.getInstance]. Otherwise, the factory
 * instance is consulted for the current time.
 */
interface CurrentTimeFactory {
    val currentTime: Calendar
}

/**
 * Abstracts away how state changes are scheduled. The [AlarmManagerStateChangeScheduler]
 * implementation schedules callbacks within the system AlarmManager. Alternate
 * implementations, such as test case mocks can subvert this behavior.
 */
interface StateChangeScheduler {
    fun scheduleInstanceStateChange(
        context: Context,
        time: Calendar,
        instance: AlarmInstance,
        newState: Int
    )

    fun cancelScheduledInstanceStateChange(context: Context, instance: AlarmInstance)
}

/**
 * Schedules state change callbacks within the AlarmManager.
 */
private class AlarmManagerStateChangeScheduler : StateChangeScheduler {

    override fun scheduleInstanceStateChange(
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
        val stateChangeIntent: Intent =
            Utils.createStateChangeIntent(context, "ALARM_MANAGER", instance, newState)
        // Treat alarm state change as high priority, use foreground broadcasts
        stateChangeIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        val pendingIntent: PendingIntent =
            PendingIntent.getService(
                context,
                instance.hashCode(),
                stateChangeIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

        val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    override fun cancelScheduledInstanceStateChange(context: Context, instance: AlarmInstance) {
        LogUtils.v("Canceling instance " + instance.mId + " timers")

        // Create a PendingIntent that will match any one set for this instance
        val pendingIntent: PendingIntent? =
            PendingIntent.getService(
                context, instance.hashCode(),
                Utils.createStateChangeIntent(context, "ALARM_MANAGER", instance, null),
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

        pendingIntent?.let {
            val am: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            am.cancel(it)
            it.cancel()
        }
    }
}
