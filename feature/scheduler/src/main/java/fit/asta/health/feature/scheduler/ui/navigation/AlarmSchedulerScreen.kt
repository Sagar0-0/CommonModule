package fit.asta.health.feature.scheduler.ui.navigation

sealed class AlarmSchedulerScreen(val route: String) {
    data object AlarmHome : AlarmSchedulerScreen(route = "alarm_home")
    data object AlarmSettingHome : AlarmSchedulerScreen(route = "alarm_setting_home")
    data object TagSelection : AlarmSchedulerScreen(route = "tag_selection")
    data object IntervalSettingsSelection : AlarmSchedulerScreen(route = "interval_selection")
    data object SpotifyScreen : AlarmSchedulerScreen(route = "spotify_screen")
    data object SpotifyLoginLauncherScreen : AlarmSchedulerScreen(route = "spotify_login_launcher_screen")
    data object SpotifyHomeScreen : AlarmSchedulerScreen(route = "spotify_home_screen")
    data object SpotifySearchScreen : AlarmSchedulerScreen(route = "spotify_search_screen")
}
