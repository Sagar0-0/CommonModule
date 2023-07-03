package fit.asta.health.thirdparty.spotify

sealed class SpotifyNavRoutes(val routes: String) {
    object TrackDetailScreen : SpotifyNavRoutes("track-details-screen")
    object AstaMusicScreen : SpotifyNavRoutes("asta-music-screen")
    object FavouriteScreen : SpotifyNavRoutes("favourite-screen")
    object ThirdPartyScreen : SpotifyNavRoutes("third-party-screen")
    object SearchScreen : SpotifyNavRoutes("search-screen")
    object ProfileScreen : SpotifyNavRoutes("profile-screen")
    object AlbumDetailScreen : SpotifyNavRoutes("album-details-screen")
}
