package fit.asta.health.tools.walking.nav

sealed class StepsCounterScreen(val route: String) {
    object StepsCounterHomeScreen : StepsCounterScreen(route = "steps_counter_home_screen")
    object MusicScreen : StepsCounterScreen(route = "music_screen")
    object DistanceScreen : StepsCounterScreen(route = "distance_screen")
    object ModeScreen : StepsCounterScreen(route = "mode_screen")
    object GoalScreen : StepsCounterScreen(route = "goals_screen")
    object TypesScreen : StepsCounterScreen(route = "types_screen")
}
