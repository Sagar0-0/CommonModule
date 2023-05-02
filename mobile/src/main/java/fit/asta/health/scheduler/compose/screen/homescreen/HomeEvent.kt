package fit.asta.health.scheduler.compose.screen.homescreen

import android.content.Context
import fit.asta.health.scheduler.model.db.entity.AlarmEntity

sealed class HomeEvent{
    data class SetAlarmState(val alarm:AlarmEntity):HomeEvent()
    data class DeleteAlarm(val alarm:AlarmEntity,val context: Context):HomeEvent()
    data class InsertAlarm(val alarm:AlarmEntity):HomeEvent()
    object AddAlarm:HomeEvent()
}
