package fit.asta.health.scheduler.compose.screen.timesettingscreen

import android.content.Context
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.RepUiState
import fit.asta.health.scheduler.compose.screen.alarmsetingscreen.StatUiState

sealed class TimeSettingEvent{
    data class SetSnooze(val time:Int):TimeSettingEvent()
    data class SetDuration(val time:Int):TimeSettingEvent()
    data class SetAdvancedDuration(val time:Int,val context: Context):TimeSettingEvent()
    data class SetAdvancedStatus(val choice:Boolean):TimeSettingEvent()
    data class RemindAtEndOfDuration(val choice:Boolean):TimeSettingEvent()
    data class SetVariantStatus(val choice:Boolean):TimeSettingEvent()
    data class SetStatus(val choice:Boolean):TimeSettingEvent()
    data class AddVariantInterval(val variantInterval:StatUiState,val context: Context):TimeSettingEvent()
    data class DeleteVariantInterval(val variantInterval:StatUiState):TimeSettingEvent()
    data class SetRepetitiveIntervals(val interval:RepUiState,val context: Context):TimeSettingEvent()
    object Save:TimeSettingEvent()
}
