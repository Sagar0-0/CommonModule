package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.netx.albums.SpotifyAlbumDetailsModelX
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.netx.me.albums.SpotifyLibraryAlbumModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.episodes.SpotifyLibraryEpisodesModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.following.SpotifyUserFollowingArtistX
import fit.asta.health.thirdparty.spotify.model.netx.me.shows.SpotifyLibraryShowsModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.tracks.SpotifyLibraryTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.playlist.SpotifyUserPlaylistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopArtistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.top.SpotifyTopTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyPlayerRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import retrofit2.Response

interface SpotifyApi {

    suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModelX>

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtistX>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModelX>

    suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModelX>

    suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModelX>

    suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModelX>

    suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModelX>

    suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModelX>

    suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModelX>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyPlayerRecentlyPlayedModelX>

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
    ): Response<SpotifyAlbumDetailsModelX>

    suspend fun getCategories(
        accessToken: String,
        country: String
    ): Response<SpotifyBrowseCategoriesModel>

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