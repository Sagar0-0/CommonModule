package fit.asta.health.thirdparty.spotify.view.events

sealed class SpotifyUiEvent {

    class PlaySong(val songUri: String) : SpotifyUiEvent()

    class SetTrackDetails(val trackId: String) : SpotifyUiEvent()

    class SetAlbumDetails(val albumId: String) : SpotifyUiEvent()

    class OpenSpotify(val uri: String) : SpotifyUiEvent()

    object LoadRecentlyPlayed : SpotifyUiEvent()

    object LoadRecommendation : SpotifyUiEvent()

    object LoadUserTopTracks : SpotifyUiEvent()

    object LoadUserTopArtists : SpotifyUiEvent()

}
