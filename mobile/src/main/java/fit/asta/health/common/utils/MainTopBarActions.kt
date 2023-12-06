package fit.asta.health.common.utils

sealed class MainTopBarActions(val route: String) {
    data object Location : MainTopBarActions("Location")
    data object Notification : MainTopBarActions("Notification")
    data object Settings : MainTopBarActions("Settings")
    data object Profile : MainTopBarActions("Profile")
    data object Share : MainTopBarActions("Share")
    data object Schedule : MainTopBarActions("Schedule")
}