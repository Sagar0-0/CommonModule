package fit.asta.health.tools.meditation.nav


sealed class MeditationScreen(val route: String) {
    data object MeditationHomeScreen : MeditationScreen(route = "meditation_home_screen")
    data object Language : MeditationScreen(route = "language_screen")
    data object Level : MeditationScreen(route = "level_screen")
    data object Instructor : MeditationScreen(route = "instructor_screen")
    data object Music : MeditationScreen(route = "music_screen")
    data object AudioMeditation : MeditationScreen(route = "audio_meditation_screen")
}