package fit.asta.health.feature.scheduler.ui.navigation

sealed class AlarmSchedulerScreen(val route: String) {
    data object AlarmHome : AlarmSchedulerScreen(route = "alarm_home")
    data object AlarmSettingHome : AlarmSchedulerScreen(route = "alarm_setting_home")
    data object TagSelection : AlarmSchedulerScreen(route = "tag_selection")
    data object IntervalSettingsSelection : AlarmSchedulerScreen(route = "interval_selection")
}
