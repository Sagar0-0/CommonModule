package fit.asta.health.navigation.today.ui.view

import android.content.Context
import fit.asta.health.common.utils.HourMinAmPm
import fit.asta.health.data.scheduler.db.entity.AlarmEntity

sealed class HomeEvent {
    data class DeleteAlarm(val alarm: AlarmEntity, val context: Context) : HomeEvent()
    data class SkipAlarm(val alarm: AlarmEntity, val context: Context) : HomeEvent()
    data class NavSchedule(val hourMinAmPm: HourMinAmPm?) : HomeEvent()
    data class EditAlarm(val alarm: AlarmEntity) : HomeEvent()
    data class SetDefaultSchedule(val context: Context) : HomeEvent()
    data object SetAlarm : HomeEvent()
    data object Retry : HomeEvent()
}
enum class Event{
    Morning, Afternoon, Evening, NextDay
}
