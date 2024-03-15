package fit.asta.health.data.spotify.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.spotify.remote.model.common.Album
import fit.asta.health.data.spotify.remote.model.common.Track
import fit.asta.health.data.spotify.remote.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.data.spotify.remote.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.data.spotify.remote.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.data.spotify.remote.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.data.spotify.remote.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.data.spotify.remote.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.data.spotify.remote.model.me.SpotifyMeModel
import fit.asta.health.data.spotify.remote.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.data.spotify.remote.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.data.spotify.remote.model.saved.SpotifyLikedSongsResponse
import fit.asta.health.data.spotify.remote.model.search.ArtistList
import fit.asta.health.data.spotify.remote.model.search.SpotifySearchModel
import fit.asta.health.data.spotify.remote.model.search.TrackList

interface SpotifyRepo {

    suspend fun getCurrentUserDetails(accessToken: String): ResponseState<SpotifyMeModel>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): ResponseState<SpotifyUserFollowingArtist>

    suspend fun getCurrentUserTopTracks(accessToken: String): ResponseState<TrackList>

    suspend fun getCurrentUserTopArtists(accessToken: String): ResponseState<ArtistList>

    suspend fun getCurrentUserAlbums(accessToken: String): ResponseState<SpotifyLibraryAlbumModel>

    suspend fun getCurrentUserShows(accessToken: String): ResponseState<SpotifyLibraryShowsModel>

    suspend fun getCurrentUserEpisodes(accessToken: String): ResponseState<SpotifyLibraryEpisodesModel>

    suspend fun getCurrentUserTracks(accessToken: String): ResponseState<SpotifyLibraryTracksModel>

    suspend fun getCurrentUserPlaylists(accessToken: String): ResponseState<SpotifyUserPlaylistsModel>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): ResponseState<SpotifyUserRecentlyPlayedModel>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): ResponseState<SpotifyUserPlaylistsModel>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): ResponseState<Track>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): ResponseState<Album>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): ResponseState<SpotifySearchModel>

    suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): ResponseState<SpotifyRecommendationModel>

    suspend fun getCurrentUserSavedSongs(
        accessToken: String,
        market: String,
        limit: String,
        offset: String
    ): ResponseState<SpotifyLikedSongsResponse>
}