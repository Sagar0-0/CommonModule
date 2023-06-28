package fit.asta.health.thirdparty.spotify

sealed class SpotifyNavRoutes(val routes: String) {

    object MainScreen : SpotifyNavRoutes("main-screen")

}
