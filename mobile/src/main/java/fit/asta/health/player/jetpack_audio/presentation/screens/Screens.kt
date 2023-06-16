package fit.asta.health.player.jetpack_audio.presentation.screens

sealed class Screens(val route: String) {
    object Home : Screens(route = "home")
    object Player : Screens(route = "player")
}
