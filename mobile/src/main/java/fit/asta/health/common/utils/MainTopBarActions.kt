package fit.asta.health.common.utils

sealed class MainTopBarActions(val route: String) {
    object Location : MainTopBarActions("Location")
    object Notification : MainTopBarActions("Notification")
    object Settings : MainTopBarActions("Settings")
    object Profile : MainTopBarActions("Profile")
    object Share : MainTopBarActions("Share")
}