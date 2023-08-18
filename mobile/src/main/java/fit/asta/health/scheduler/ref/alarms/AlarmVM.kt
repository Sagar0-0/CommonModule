package fit.asta.health.scheduler.ref.alarms

import android.content.Context
import android.content.Intent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.ref.LogUtils
import fit.asta.health.scheduler.ref.Utils
import fit.asta.health.scheduler.ref.data.Weekdays
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.db.AlarmRefDao
import fit.asta.health.scheduler.ref.provider.Alarm
import fit.asta.health.scheduler.ref.provider.AlarmInstance
import fit.asta.health.scheduler.ref.provider.ClockContract
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

@HiltViewModel
class AlarmVM @Inject constructor(
    private val alarmRefDao: AlarmRefDao,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val savedStateHandle: SavedStateHandle,
    private val alarmDataManager: AlarmDataManager
) : ViewModel() {

    private var mSelectedAlarm: Alarm? = null

    fun setAlarmEnabled(alarm: Alarm, newState: Boolean, context: Context) {
        if (newState != alarm.enabled) {
            alarm.enabled = newState
            asyncUpdateAlarm(alarm, minorUpdate = false, mAppContext = context)
            LogUtils.d("Updating alarm enabled state to $newState")
        }
    }

    fun setAlarmVibrationEnabled(alarm: Alarm, newState: Boolean, context: Context) {
        if (newState != alarm.vibrate) {
            alarm.vibrate = newState
            asyncUpdateAlarm(alarm, minorUpdate = true, mAppContext = context)
            LogUtils.d("Updating vibrate state to $newState")
        }
    }

    fun setAlarmRepeatEnabled(alarm: Alarm, isEnabled: Boolean, context: Context) {
        val alarmId = alarm.id.toString()
        if (isEnabled) {
            // Set all previously set days
            // or
            // Set all days if no previous.
            val bitSet: Int = savedStateHandle[alarmId]!!
            alarm.daysOfWeek = Weekdays.fromBits(bitSet)
            if (!alarm.daysOfWeek.isRepeating) {
                alarm.daysOfWeek = Weekdays.ALL
            }
        } else {
            // Remember the set days in case the user wants it back.
            savedStateHandle[alarmId] = alarm.daysOfWeek.bits
            // Remove all repeat days
            alarm.daysOfWeek = Weekdays.NONE
        }

        // if the change altered the next scheduled alarm time, tell the user
        asyncUpdateAlarm(alarm, minorUpdate = false, mAppContext = context)
    }

    fun setDayOfWeekEnabled(alarm: Alarm, checked: Boolean, index: Int, context: Context) {

        val weekday = Weekdays.Order.SUN_TO_SAT.calendarDays[index]
        alarm.daysOfWeek = alarm.daysOfWeek.setBit(weekday, checked)

        // if the change altered the next scheduled alarm time, tell the user
        asyncUpdateAlarm(alarm, minorUpdate = false, context)
    }

    fun onDeleteClicked(alarm: Alarm, context: Context) {
        asyncDeleteAlarm(alarm, context)
        LogUtils.d("Deleting alarm.")
    }


    fun dismissAlarmInstance(alarmInstance: AlarmInstance, context: Context) {
        val dismissIntent: Intent = Utils.createStateChangeIntent(
            context, Utils.ALARM_DISMISS_TAG, alarmInstance,
            ClockContract.InstancesColumns.PREDISMISSED_STATE
        )
        context.startService(dismissIntent)
    }


    fun onTimeSet(hourOfDay: Int, minute: Int, context: Context) {
        if (mSelectedAlarm == null) {
            // If mSelectedAlarm is null then we're creating a new alarm.
            val alarm = Alarm(
                hour = hourOfDay,
                minutes = minute,
                enabled = true,
                id = (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
                daysOfWeek = Weekdays.ALL
            )
            asyncAddAlarm(alarm, context)
        } else {
            mSelectedAlarm!!.hour = hourOfDay
            mSelectedAlarm!!.minutes = minute
            mSelectedAlarm!!.enabled = true
            asyncUpdateAlarm(mSelectedAlarm!!, minorUpdate = false, context)
            mSelectedAlarm = null
        }
    }


    // -------------------------------------------------------------------
    private fun asyncAddAlarm(alarm: Alarm, context: Context) {
        // Add alarm to db
        viewModelScope.launch {
            alarmRefDao.insertAndUpdate(alarm)
        }
        // Create and add instance to db
        if (alarm.enabled) {
            setupAlarmInstance(alarm, context)
        }
    }

    private fun asyncUpdateAlarm(
        alarm: Alarm,
        minorUpdate: Boolean,
        mAppContext: Context
    ) {
        viewModelScope.launch {
            // Update alarm
            alarmRefDao.insertAndUpdate(alarm)
            if (minorUpdate) {
                // just update the instance in the database and update notifications.
                val instanceList = alarmInstanceDao.getInstancesByAlarmId(alarm.id)
                for (instance in instanceList) {
                    instance.mVibrate = alarm.vibrate
                    instance.mRingtone = alarm.alert.toString()
                    instance.mLabel = alarm.label

                    alarmInstanceDao.insertAndUpdate(instance)
                    // Update the notification for this instance.
                    AlarmNotifications.updateNotification(mAppContext, instance)
                }
            }
        }
    }

    private fun asyncDeleteAlarm(alarm: Alarm, mAppContext: Context) {
        alarmDataManager.deleteAllInstances(mAppContext, alarm.id)
        viewModelScope.launch { alarmRefDao.delete(alarm) }
    }

    private fun setupAlarmInstance(alarm: Alarm, mAppContext: Context) {
        val newInstance = alarm.createInstanceAfter(Calendar.getInstance())
        viewModelScope.launch {
            alarmInstanceDao.getInstancesByAlarmId(newInstance.mAlarmId!!)
                .forEach { otherInstances ->
                    if (otherInstances.alarmTime == newInstance.alarmTime) {
                        newInstance.mId = otherInstances.mId
                        alarmInstanceDao.insertAndUpdate(newInstance)
                    }
                }
            alarmInstanceDao.insertAndUpdate(newInstance)
        }
        // Register instance to state manager
        alarmDataManager.registerInstance(mAppContext, newInstance, true)
    }

}