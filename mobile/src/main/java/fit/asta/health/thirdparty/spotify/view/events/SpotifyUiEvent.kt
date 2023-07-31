package fit.asta.health.thirdparty.spotify.view.events

import fit.asta.health.thirdparty.spotify.model.net.common.Album

sealed class SpotifyUiEvent {

    sealed class HelperEvent : SpotifyUiEvent() {
        class PlaySong(val songUri: String) : HelperEvent()

        class SetTrackId(val trackId: String) : HelperEvent()

        class SetAlbumId(val albumId: String) : HelperEvent()
    }

    sealed class NetworkIO : SpotifyUiEvent() {
        object LoadRecentlyPlayed : NetworkIO()

        object LoadRecommendation : NetworkIO()

        object LoadUserTopTracks : NetworkIO()

        object LoadUserTopArtists : NetworkIO()

        object LoadAlbumDetails : NetworkIO()

        object LoadCurrentUserTracks : NetworkIO()

        object LoadCurrentUserPlaylist : NetworkIO()

        object LoadCurrentUserArtists : NetworkIO()

        object LoadCurrentUserAlbum : NetworkIO()

        object LoadCurrentUserShows : NetworkIO()

        object LoadCurrentUserEpisode : NetworkIO()
    }

    sealed class LocalIO : SpotifyUiEvent() {
        object LoadAllAlbums : LocalIO()

        class InsertAlbumData(val album: Album) : LocalIO()

        class DeleteAlbumData(val album: Album) : LocalIO()

        object LoadAllTracks : LocalIO()
    }
}