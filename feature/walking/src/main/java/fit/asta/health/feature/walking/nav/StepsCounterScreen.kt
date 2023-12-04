package fit.asta.health.feature.walking.nav

sealed class StepsCounterScreen(val route: String) {
    data object StepsCounterHomeScreen : StepsCounterScreen(route = "steps_counter_home_screen")
    data object StepsPermissionScreen : StepsCounterScreen(route = "steps_permission_screen")
    data object StepsProgressScreen : StepsCounterScreen(route = "steps_progress_screen")
    data object StepsSheetScreen : StepsCounterScreen(route = "steps_sheet_screen")

}
