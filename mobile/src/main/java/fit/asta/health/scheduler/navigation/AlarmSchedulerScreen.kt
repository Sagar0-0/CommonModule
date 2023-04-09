package fit.asta.health.scheduler.navigation

sealed class AlarmSchedulerScreen(val route: String) {
    object AlarmSettingHome : AlarmSchedulerScreen(route = "alarm_setting_home")
    object TagSelection : AlarmSchedulerScreen(route = "tag_selection")
    object IntervalSettingsSelection : AlarmSchedulerScreen(route = "interval_selection")
}
