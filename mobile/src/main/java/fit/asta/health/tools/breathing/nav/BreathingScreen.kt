package fit.asta.health.tools.breathing.nav

sealed class BreathingScreen(val route: String){
    object HomeScreen : BreathingScreen(route = "breathing_home_screen")
    object GoalScreen : BreathingScreen(route = "breathing_goal_screen")
    object BreakTimeScreen : BreathingScreen(route = "breathing_break_time_screen")
    object CourseLevelScreen : BreathingScreen(route = "breathing_level_screen")
    object ExerciseScreen : BreathingScreen(route = "breathing_exercise_screen")
    object LanguageScreen : BreathingScreen(route = "breathing_language_screen")
    object MusicScreen : BreathingScreen(route = "breathing_music_screen")
    object MusicMeditationScreen : BreathingScreen(route = "breathing_music_meditation_screen")
    object MusicPlayerScreen : BreathingScreen(route = "breathing_music_player_screen")
    object PaceScreen : BreathingScreen(route = "breathing_pace_screen")
    object InstructorScreen : BreathingScreen(route = "breathing_instructor_screen")

}
