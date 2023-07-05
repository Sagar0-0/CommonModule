package fit.asta.health.thirdparty.spotify.model.api

import fit.asta.health.thirdparty.spotify.model.net.common.Album
import fit.asta.health.thirdparty.spotify.model.net.library.albums.SpotifyLibraryAlbumModel
import fit.asta.health.thirdparty.spotify.model.net.library.episodes.SpotifyLibraryEpisodesModel
import fit.asta.health.thirdparty.spotify.model.net.library.following.SpotifyUserFollowingArtist
import fit.asta.health.thirdparty.spotify.model.net.library.shows.SpotifyLibraryShowsModel
import fit.asta.health.thirdparty.spotify.model.net.library.tracks.SpotifyLibraryTracksModel
import fit.asta.health.thirdparty.spotify.model.net.library.playlist.SpotifyUserPlaylistsModel
import fit.asta.health.thirdparty.spotify.model.net.search.SpotifySearchModel
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import fit.asta.health.thirdparty.spotify.model.net.search.TrackList
import fit.asta.health.thirdparty.spotify.model.net.common.Track
import fit.asta.health.thirdparty.spotify.model.net.me.SpotifyMeModel
import fit.asta.health.thirdparty.spotify.model.net.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.thirdparty.spotify.model.net.recommendations.SpotifyRecommendationModel
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApiService {

    @GET("me")
    suspend fun getCurrentUserDetails(
        @HeaderMap headers: Map<String, String>
    ): Response<SpotifyMeModel>

    @GET("me/following")
    suspend fun getCurrentUserFollowedArtists(
        @HeaderMap headers: Map<String, String>,
        @Query("type") type: String = "artist"
    ): Response<SpotifyUserFollowingArtist>

    @GET("me/top/tracks")
    suspend fun getCurrentUserTopTracks(
        @HeaderMap headers: Map<String, String>
    ): Response<TrackList>

    @GET("me/top/artists")
    suspend fun getCurrentUserTopArtists(
        @HeaderMap headers: Map<String, String>
    ): Response<ArtistList>

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
    ): Response<SpotifyUserRecentlyPlayedModel>

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylists(
        @HeaderMap headers: Map<String, String>,
        @Path("user_id") userID: String
    ): Response<SpotifyUserPlaylistsModel>

    @GET("tracks/{track_id}")
    suspend fun getTrackDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("track_id") trackID: String
    ): Response<Track>

    @GET("albums/{album_id}")
    suspend fun getAlbumDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("album_id") albumID: String
    ): Response<Album>

    @GET("search")
    suspend fun searchQuery(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifySearchModel>

    @GET("recommendations")
    suspend fun getRecommendations(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyRecommendationModel>
}