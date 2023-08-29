package fit.asta.health.feature.spotify.navigation

sealed class SpotifyNavRoutes(val routes: String) {
    data object TrackDetailScreen : SpotifyNavRoutes("track-details-screen")
    data object AstaMusicScreen : SpotifyNavRoutes("asta-music-screen")
    data object FavouriteScreen : SpotifyNavRoutes("favourite-screen")
    data object ThirdPartyScreen : SpotifyNavRoutes("third-party-screen")
    data object SearchScreen : SpotifyNavRoutes("search-screen")
    data object ProfileScreen : SpotifyNavRoutes("profile-screen")
    data object AlbumDetailScreen : SpotifyNavRoutes("album-details-screen")
}
