package fit.asta.health.feature.water.nav

sealed class WaterScreen(val route: String) {
    data object WaterToolHomeScreen : WaterScreen(route = "water_tool_home_screen")
}
