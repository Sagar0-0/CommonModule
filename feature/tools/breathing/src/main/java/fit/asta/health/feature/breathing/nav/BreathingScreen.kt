package fit.asta.health.feature.breathing.nav

sealed class BreathingScreen(val route: String){
    data object HomeScreen : BreathingScreen(route = "breathing_home_screen")
    data object MusicScreen : BreathingScreen(route = "breathing_music_screen")
    data object SheetScreen : BreathingScreen(route = "breathing_sheet_screen")
    data object ExerciseScreen : BreathingScreen(route = "breathing_exercise_screen")
}
