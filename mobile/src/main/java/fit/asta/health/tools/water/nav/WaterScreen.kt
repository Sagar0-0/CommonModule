package fit.asta.health.tools.water.nav

sealed class WaterScreen(val route: String) {
    object WaterToolHomeScreen : WaterScreen(route = "water_tool_home_screen")
    object WorkScreen : WaterScreen(route = "work_screen")
    object LifeStyleScreen : WaterScreen(route = "lifestyle_screen")
}
