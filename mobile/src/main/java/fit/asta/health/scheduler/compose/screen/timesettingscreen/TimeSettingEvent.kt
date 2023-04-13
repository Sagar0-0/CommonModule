package fit.asta.health.scheduler.compose.screen.timesettingscreen

sealed class TimeSettingEvent{
    data class SetSnooze(val time:Int):TimeSettingEvent()
    data class SetDuration(val time:Int):TimeSettingEvent()
    data class SetAdvancedDuration(val time:Int):TimeSettingEvent()
    data class SetAdvancedStatus(val choice:Boolean):TimeSettingEvent()
}
