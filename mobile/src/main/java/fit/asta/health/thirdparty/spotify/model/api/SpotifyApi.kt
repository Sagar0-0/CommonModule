package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
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

    suspend fun getCurrentUserFollowedArtists(accessToken: String): Response<SpotifyUserFollowingArtist>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<SpotifyTopTracksModelX>

    suspend fun getCurrentUserTopArtists(accessToken: String): Response<SpotifyTopArtistsModelX>

    suspend fun getCurrentUserAlbums(accessToken: String): Response<SpotifyLibraryAlbumModel>

    suspend fun getCurrentUserShows(accessToken: String): Response<SpotifyLibraryShowsModel>

    suspend fun getCurrentUserEpisodes(accessToken: String): Response<SpotifyLibraryEpisodesModel>

    suspend fun getCurrentUserTracks(accessToken: String): Response<SpotifyLibraryTracksModel>

    suspend fun getCurrentUserPlaylists(accessToken: String): Response<SpotifyUserPlaylistsModel>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyPlayerRecentlyPlayedModelX>

    suspend fun getUserPlaylists(
        accessToken: String,
        userID: String
    ): Response<SpotifyUserPlaylistsModel>

    suspend fun getTrackDetails(
        accessToken: String,
        trackID: String
    ): Response<TrackX>

    suspend fun getAlbumDetails(
        accessToken: String,
        albumID: String
    ): Response<SpotifyAlbumDetailsModel>

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