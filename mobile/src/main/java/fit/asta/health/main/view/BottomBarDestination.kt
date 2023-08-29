package fit.asta.health.main.view

sealed class BottomBarDestination(val route: String) {
    data object Home : BottomBarDestination("bbd_home")
    data object Today : BottomBarDestination("bbd_today")
    data object Track : BottomBarDestination("bbd_track")
}