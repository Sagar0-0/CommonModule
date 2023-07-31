package fit.asta.health.thirdparty.spotify.view.events

import fit.asta.health.thirdparty.spotify.model.net.common.Album

sealed class SpotifyUiEvent {

    class PlaySong(val songUri: String) : SpotifyUiEvent()

    class SetTrackDetails(val trackId: String) : SpotifyUiEvent()

    class SetAlbumDetails(val albumId: String) : SpotifyUiEvent()

    class OpenSpotify(val uri: String) : SpotifyUiEvent()

    object LoadRecentlyPlayed : SpotifyUiEvent()

    object LoadRecommendation : SpotifyUiEvent()

    object LoadUserTopTracks : SpotifyUiEvent()

    object LoadUserTopArtists : SpotifyUiEvent()

    object LoadAlbumDetails : SpotifyUiEvent()

    object LoadLocalAlbumDetails : SpotifyUiEvent()

    class InsertAlbumData(val album: Album) : SpotifyUiEvent()

    class DeleteAlbumData(val album: Album) : SpotifyUiEvent()
}
