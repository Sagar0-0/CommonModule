package fit.asta.health.scheduler.compose.screen.homescreen

import fit.asta.health.scheduler.model.db.entity.AlarmEntity

sealed class HomeEvent {
    data class DeleteAlarm(val alarm: AlarmEntity) : HomeEvent()
    data class RemoveAlarm(val alarm: AlarmEntity, val event: Event) : HomeEvent()
    data class UndoAlarm(val alarm: AlarmEntity, val event: Event) : HomeEvent()
    data class SkipAlarm(val alarm: AlarmEntity) : HomeEvent()
    data class EditAlarm(val alarm: AlarmEntity) : HomeEvent()
    object SetAlarm : HomeEvent()
    object SetDefaultSchedule : HomeEvent()
}
enum class Event{
    Morning, Afternoon, Evening, NextDay
}
