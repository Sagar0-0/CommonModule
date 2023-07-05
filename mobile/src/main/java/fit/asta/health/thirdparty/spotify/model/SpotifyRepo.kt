package fit.asta.health.thirdparty.spotify.model

import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.model.netx.library.albums.SpotifyLibraryAlbumModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.episodes.SpotifyLibraryEpisodesModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.following.SpotifyUserFollowingArtistX
import fit.asta.health.thirdparty.spotify.model.netx.library.shows.SpotifyLibraryShowsModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.tracks.SpotifyLibraryTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.library.playlist.SpotifyUserPlaylistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.ArtistListX
import fit.asta.health.thirdparty.spotify.model.netx.search.TrackListX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyUserRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import retrofit2.Response

interface SpotifyRepo {

    suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModelX>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtistX>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackListX>

    suspend fun getCurrentUserTopArtists(accessToken: String): Response<ArtistListX>

    suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModelX>

    suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModelX>

    suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModelX>

    suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModelX>

    suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModelX>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyUserRecentlyPlayedModelX>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModelX>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<TrackX>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<AlbumX>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModelX>

    suspend fun getRecommendations(
        accessToken: String,
        seedArtists: String,
        seedGenres: String,
        seedTracks: String,
        limit: String
    ): Response<SpotifyRecommendationModelX>
}