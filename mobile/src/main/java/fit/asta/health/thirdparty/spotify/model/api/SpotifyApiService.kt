package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.net.categories.SpotifyBrowseCategoriesModel
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import fit.asta.health.thirdparty.spotify.model.netx.me.albums.SpotifyLibraryAlbumModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.episodes.SpotifyLibraryEpisodesModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.following.SpotifyUserFollowingArtistX
import fit.asta.health.thirdparty.spotify.model.netx.me.shows.SpotifyLibraryShowsModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.tracks.SpotifyLibraryTracksModelX
import fit.asta.health.thirdparty.spotify.model.netx.me.playlist.SpotifyUserPlaylistsModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.SpotifySearchModelX
import fit.asta.health.thirdparty.spotify.model.netx.search.ArtistListX
import fit.asta.health.thirdparty.spotify.model.netx.search.TrackListX
import fit.asta.health.thirdparty.spotify.model.netx.common.TrackX
import fit.asta.health.thirdparty.spotify.model.netx.me.SpotifyMeModelX
import fit.asta.health.thirdparty.spotify.model.netx.recently.SpotifyUserRecentlyPlayedModelX
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
    ): Response<SpotifyUserFollowingArtistX>

    @GET("me/top/tracks")
    suspend fun getCurrentUserTopTracks(
        @HeaderMap headers: Map<String, String>
    ): Response<TrackListX>

    @GET("me/top/artists")
    suspend fun getCurrentUserTopArtists(
        @HeaderMap headers: Map<String, String>
    ): Response<ArtistListX>

    @GET("me/playlists")
    suspend fun getCurrentUserPlaylists(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyUserPlaylistsModelX>

    @GET("me/albums")
    suspend fun getCurrentUserAlbums(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryAlbumModelX>

    @GET("me/shows")
    suspend fun getCurrentUserShows(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryShowsModelX>

    @GET("me/episodes")
    suspend fun getCurrentUserEpisodes(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryEpisodesModelX>

    @GET("me/tracks")
    suspend fun getCurrentUserTracks(
        @HeaderMap headers: Map<String, String>,
    ): Response<SpotifyLibraryTracksModelX>

    @GET("me/player/recently-played")
    suspend fun getCurrentUserRecentlyPlayedTracks(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyUserRecentlyPlayedModelX>

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylists(
        @HeaderMap headers: Map<String, String>,
        @Path("user_id") userID: String
    ): Response<SpotifyUserPlaylistsModelX>

    @GET("tracks/{track_id}")
    suspend fun getTrackDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("track_id") trackID: String
    ): Response<TrackX>

    @GET("albums/{album_id}")
    suspend fun getAlbumDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("album_id") albumID: String
    ): Response<AlbumX>

    @GET("browse/categories")
    suspend fun getCategories(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyBrowseCategoriesModel>

    @GET("search")
    suspend fun searchQuery(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifySearchModelX>

    @GET("recommendations")
    suspend fun getRecommendations(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyRecommendationModelX>
}