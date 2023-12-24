package fit.asta.health.meditation.nav


sealed class MeditationScreen(val route: String) {
    data object MeditationHomeScreen : MeditationScreen(route = "meditation_home_screen")
    data object SheetScreen : MeditationScreen(route = "sheet_screen")
    data object AudioMeditation : MeditationScreen(route = "audio_meditation_screen")
}