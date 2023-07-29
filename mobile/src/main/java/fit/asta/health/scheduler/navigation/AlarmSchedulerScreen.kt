package fit.asta.health.scheduler.navigation

sealed class AlarmSchedulerScreen(val route: String) {
    object AlarmHome : AlarmSchedulerScreen(route = "alarm_home")
    object AlarmSettingHome : AlarmSchedulerScreen(route = "alarm_setting_home")
    object TagSelection : AlarmSchedulerScreen(route = "tag_selection")
    object IntervalSettingsSelection : AlarmSchedulerScreen(route = "interval_selection")
    object Spotify : AlarmSchedulerScreen(route = "spotify_screen")
}
