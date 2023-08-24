package fit.asta.health.thirdparty.spotify.data.repo

import fit.asta.health.thirdparty.spotify.data.model.common.Album
import fit.asta.health.thirdparty.spotify.data.model.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.data.model.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.data.model.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.data.model.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.data.model.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.data.model.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.data.model.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.data.model.search.ArtistList
import fit.asta.health.thirdparty.spotify.data.model.search.TrackList
import fit.asta.health.thirdparty.spotify.data.model.common.Track
import fit.asta.health.thirdparty.spotify.data.model.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.data.model.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.data.model.recommendations.SpotifyRecommendationModel
import fit.asta.health.thirdparty.spotify.utils.SpotifyNetworkCall
import kotlinx.coroutines.flow.Flow

interface SpotifyRepo {

    suspend fun getCurrentUserDetails(accessToken: String): Flow<SpotifyNetworkCall<SpotifyMeModel>>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Flow<SpotifyNetworkCall<SpotifyUserFollowingArtist>>

    suspend fun getCurrentUserTopTracks(accessToken: String): Flow<SpotifyNetworkCall<TrackList>>

    suspend fun getCurrentUserTopArtists(accessToken: String): Flow<SpotifyNetworkCall<ArtistList>>

    suspend fun getCurrentUserAlbums(accessToken: String): Flow<SpotifyNetworkCall<SpotifyLibraryAlbumModel>>

    suspend fun getCurrentUserShows(accessToken: String): Flow<SpotifyNetworkCall<SpotifyLibraryShowsModel>>

    suspend fun getCurrentUserEpisodes(accessToken: String): Flow<SpotifyNetworkCall<SpotifyLibraryEpisodesModel>>

    suspend fun getCurrentUserTracks(accessToken: String): Flow<SpotifyNetworkCall<SpotifyLibraryTracksModel>>

    suspend fun getCurrentUserPlaylists(accessToken: String): Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Flow<SpotifyNetworkCall<SpotifyUserRecentlyPlayedModel>>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Flow<SpotifyNetworkCall<SpotifyUserPlaylistsModel>>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Flow<SpotifyNetworkCall<Track>>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Flow<SpotifyNetworkCall<Album>>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Flow<SpotifyNetworkCall<SpotifySearchModel>>

    suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Flow<SpotifyNetworkCall<SpotifyRecommendationModel>>
}