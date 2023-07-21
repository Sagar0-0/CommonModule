package fit.asta.health.main.ui

sealed class BottomBarScreens(val route: String) {
    object Home : BottomBarScreens("bb_home")
    object Today : BottomBarScreens("bb_today")
    object Track : BottomBarScreens("bb_track")
}