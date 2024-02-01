package fit.asta.health.navigation.alarms.ui

import android.content.Context
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.data.scheduler.db.entity.AlarmEntity

sealed class AlarmEvent {
    data class SetAlarmState(
        val state: Boolean, val alarm: AlarmEntity,
        val context: Context
    ) : AlarmEvent()

    data class NavSchedule(val hourMinAmPm: HourMinAmPm?) : AlarmEvent()
    data class EditAlarm(val alarmId: Long) : AlarmEvent()
    data object OnBack : AlarmEvent()
    data object SetAlarm : AlarmEvent()
}
