package fit.asta.health.feature.scheduler.ui.navigation

sealed class SpotifyNavRoutes(val routes: String) {
    data object HomeScreen : SpotifyNavRoutes("spotify-home")
    data object SearchScreen : SpotifyNavRoutes("spotify-search")
}
