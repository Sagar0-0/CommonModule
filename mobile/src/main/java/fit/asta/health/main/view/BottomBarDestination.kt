package fit.asta.health.main.view

sealed class BottomBarDestination(val route: String) {
    data object Today : BottomBarDestination("bbd_today")
    data object Tools : BottomBarDestination("bbd_tools")
    data object Track : BottomBarDestination("bbd_track")
}