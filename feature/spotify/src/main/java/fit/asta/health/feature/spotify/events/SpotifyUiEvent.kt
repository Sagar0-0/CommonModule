package fit.asta.health.feature.spotify.events

import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track

sealed class SpotifyUiEvent {

    sealed class HelperEvent : SpotifyUiEvent() {

        class PlaySong(val songUri: String) : HelperEvent()

        class SetTrackId(val trackId: String) : HelperEvent()

        class SetAlbumId(val albumId: String) : HelperEvent()

        class SetSearchQueriesAndVariables(val query: String, val type: String) : HelperEvent()
    }

    sealed class NetworkIO : SpotifyUiEvent() {

        data object LoadCurrentUserRecentlyPlayedTracks : NetworkIO()

        data object LoadRecommendationTracks : NetworkIO()

        data object LoadUserTopTracks : NetworkIO()

        data object LoadUserTopArtists : NetworkIO()

        data object LoadAlbumDetails : NetworkIO()

        data object LoadCurrentUserTracks : NetworkIO()

        data object LoadCurrentUserPlaylist : NetworkIO()

        data object LoadCurrentUserArtists : NetworkIO()

        data object LoadCurrentUserAlbum : NetworkIO()

        data object LoadCurrentUserShows : NetworkIO()

        data object LoadCurrentUserEpisode : NetworkIO()

        data object LoadSpotifySearchResult : NetworkIO()

        data object LoadTrackDetails : NetworkIO()

        data object LoadLikedSongs : NetworkIO()
    }

    sealed class LocalIO : SpotifyUiEvent() {

        data object LoadAllTracks : LocalIO()

        data object LoadAllAlbums : LocalIO()

        class InsertTrack(val track: Track) : LocalIO()

        class DeleteTrack(val track: Track) : LocalIO()

        class InsertAlbum(val album: Album) : LocalIO()

        class DeleteAlbum(val album: Album) : LocalIO()
    }
}