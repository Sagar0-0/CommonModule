package fit.asta.health.main.view

sealed class BottomBarDestination(val route: String) {
    object Home : BottomBarDestination("bbd_home")
    object Today : BottomBarDestination("bbd_today")
    object Track : BottomBarDestination("bbd_track")
}