package fit.asta.health.scheduler.compose.screen.homescreen

import android.content.Context
import fit.asta.health.scheduler.model.db.entity.AlarmEntity

sealed class HomeEvent {
    data class DeleteAlarm(val alarm: AlarmEntity, val context: Context,val event: Event) : HomeEvent()
    data class UndoAlarm(val alarm: AlarmEntity, val context: Context,val event: Event) : HomeEvent()
    data class SkipAlarm(val alarm: AlarmEntity, val context: Context,val event: Event) : HomeEvent()
    data class UndoSkipAlarm(val alarm: AlarmEntity, val context: Context,val event: Event) : HomeEvent()
    data class EditAlarm(val alarm: AlarmEntity) : HomeEvent()
    object SetAlarm : HomeEvent()
}
enum class Event{
    Morning, Afternoon, Evening, NextDay
}
