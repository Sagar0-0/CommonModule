package fit.asta.health.scheduler.ref.alarms

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fit.asta.health.scheduler.ref.data.Weekdays
import fit.asta.health.scheduler.ref.db.AlarmInstanceDao
import fit.asta.health.scheduler.ref.db.AlarmRefDao
import fit.asta.health.scheduler.ref.newalarm.StateManager
import fit.asta.health.scheduler.ref.provider.Alarm
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject

@HiltViewModel
class AlarmVM @Inject constructor(
    private val alarmRefDao: AlarmRefDao,
    private val alarmInstanceDao: AlarmInstanceDao,
    private val savedStateHandle: SavedStateHandle,
    private val alarmDataManager: AlarmDataManager,
    private val stateManager: StateManager
) : ViewModel() {

    private var mSelectedAlarm: Alarm? = null


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


    fun onTimeSet(context: Context) {
        val alarm = Alarm(
            hour = 14,
            minutes = 0,
            enabled = true,
            id = (System.currentTimeMillis() + AtomicLong().incrementAndGet()),
            daysOfWeek = Weekdays.ALL,
            preEnabled = true,
            preNotification = 10,
            endEnabled = true,
            endHour = 16,
            endMinutes = 0,
            snooze = 5,
            imgUrl = "/tags/Breathing+Tag.png",
            alert = "https://p.scdn.co/mp3-preview/541e4aaccc03318918dabf72f93e02bca7dfedcc?cid=8f5ba8ca7b2a479aa6f766c931a6e8c4",
            deleteAfterUse = false
        )
        viewModelScope.launch {
            alarmRefDao.insertAndUpdate(alarm)
        }
        stateManager.registerAlarm(context, alarm)
    }


    // -------------------------------------------------------------------

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
//                val instanceList = alarmInstanceDao.getInstancesByAlarmId(alarm.id)
//                for (instance in instanceList) {
//                    instance.mVibrate = alarm.vibrate
//                    instance.mRingtone = alarm.alert.toString()
//                    instance.mLabel = alarm.label
//
//                    alarmInstanceDao.insertAndUpdate(instance)
//                    // Update the notification for this instance.
//                    AlarmNotifications.updateNotification(mAppContext, instance)
//                }
            }
        }
    }

}