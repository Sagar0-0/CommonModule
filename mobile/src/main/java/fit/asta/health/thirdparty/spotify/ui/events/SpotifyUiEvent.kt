package fit.asta.health.thirdparty.spotify.ui.events

import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.common.Track

sealed class SpotifyUiEvent {

    sealed class HelperEvent : SpotifyUiEvent() {

        class PlaySong(val songUri: String) : HelperEvent()

        class SetTrackId(val trackId: String) : HelperEvent()

        class SetAlbumId(val albumId: String) : HelperEvent()

        class SetSearchQueriesAndVariables(val query: String, val type: String) : HelperEvent()
    }

    sealed class NetworkIO : SpotifyUiEvent() {

        object LoadCurrentUserRecentlyPlayedTracks : NetworkIO()

        object LoadRecommendationTracks : NetworkIO()

        object LoadUserTopTracks : NetworkIO()

        object LoadUserTopArtists : NetworkIO()

        object LoadAlbumDetails : NetworkIO()

        object LoadCurrentUserTracks : NetworkIO()

        object LoadCurrentUserPlaylist : NetworkIO()

        object LoadCurrentUserArtists : NetworkIO()

        object LoadCurrentUserAlbum : NetworkIO()

        object LoadCurrentUserShows : NetworkIO()

        object LoadCurrentUserEpisode : NetworkIO()

        object LoadSpotifySearchResult : NetworkIO()

        object LoadTrackDetails : NetworkIO()
    }

    sealed class LocalIO : SpotifyUiEvent() {

        object LoadAllTracks : LocalIO()

        object LoadAllAlbums : LocalIO()

        class InsertTrack(val track: Track) : LocalIO()

        class DeleteTrack(val track: Track) : LocalIO()

        class InsertAlbum(val album: Album) : LocalIO()

        class DeleteAlbum(val album: Album) : LocalIO()
    }
}