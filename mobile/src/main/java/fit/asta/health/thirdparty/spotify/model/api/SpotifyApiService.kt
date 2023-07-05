package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.net.albums.SpotifyAlbumDetailsModel
import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.net.me.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.me.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.me.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.me.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.me.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopArtistsModel
import fit.asta.health.thirdparty.spotify.model.net.top.SpotifyTopTracksModel
import fit.asta.health.thirdparty.spotify.model.net.tracks.SpotifyTrackDetailsModel
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyPlayerRecentlyPlayedModelX
import fit.asta.health.thirdparty.spotify.model.netx.recommendations.SpotifyRecommendationModelX
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApiService {

    @GET("me")
    suspend fun getCurrentUserDetails(
        @HeaderMap headers: Map<String, String>
    ): Response<SpotifyMeModelX>

    @GET("me/following")
    suspend fun getCurrentUserFollowedArtists(
        @HeaderMap headers: Map<String, String>,
        @Query("type") type: String = "artist"
    ): Response<SpotifyUserFollowingArtist>

    @GET("me/top/tracks")
    suspend fun getCurrentUserTopTracks(
        @HeaderMap headers: Map<String, String>
    ): Response<SpotifyTopTracksModel>

    @GET("me/top/artists")
    suspend fun getCurrentUserTopArtists(
        @HeaderMap headers: Map<String, String>
    ): Response<SpotifyTopArtistsModel>

    @GET("me/playlists")
    suspend fun getCurrentUserPlaylists(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyUserPlaylistsModel>

    @GET("me/albums")
    suspend fun getCurrentUserAlbums(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryAlbumModel>

    @GET("me/shows")
    suspend fun getCurrentUserShows(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryShowsModel>

    @GET("me/episodes")
    suspend fun getCurrentUserEpisodes(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryEpisodesModel>

    @GET("me/tracks")
    suspend fun getCurrentUserTracks(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryTracksModel>

    @GET("me/player/recently-played")
    suspend fun getCurrentUserRecentlyPlayedTracks(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyPlayerRecentlyPlayedModelX>

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylists(
        @HeaderMap headers: Map<String, String>,
        @Path("user_id") userID: String
    ): Response<SpotifyUserPlaylistsModel>

    @GET("tracks/{track_id}")
    suspend fun getTrackDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("track_id") trackID: String
    ): Response<SpotifyTrackDetailsModel>

    @GET("albums/{album_id}")
    suspend fun getAlbumDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("album_id") albumID: String
    ): Response<SpotifyAlbumDetailsModel>

    @GET("browse/categories")
    suspend fun getCategories(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyBrowseCategoriesModel>

    @GET("search")
    suspend fun searchQuery(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifySearchModel>

    @GET("recommendations")
    suspend fun getRecommendations(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyRecommendationModelX>
}