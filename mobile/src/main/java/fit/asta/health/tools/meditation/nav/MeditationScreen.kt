package fit.asta.health.tools.meditation.nav


sealed class MeditationScreen(val route: String) {
    object MeditationHomeScreen : MeditationScreen(route = "meditation_home_screen")
    object Language : MeditationScreen(route = "language_screen")
    object Level : MeditationScreen(route = "level_screen")
    object Instructor : MeditationScreen(route = "instructor_screen")
    object Music : MeditationScreen(route = "music_screen")
    object AudioMeditation : MeditationScreen(route = "audio_meditation_screen")
}