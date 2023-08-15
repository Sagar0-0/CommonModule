package fit.asta.health.scheduler.ui.navigation

sealed class SpotifyNavRoutes(val routes: String) {
    object HomeScreen : SpotifyNavRoutes("spotify-home")
    object SearchScreen : SpotifyNavRoutes("spotify-search")
}
