package fit.asta.health.data.spotify.remote

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
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface SpotifyApi {

    @GET("me")
    suspend fun getCurrentUserDetails(
        @HeaderMap headers: Map<String, String>
    ): SpotifyMeModel

    @GET("me/following")
    suspend fun getCurrentUserFollowedArtists(
        @HeaderMap headers: Map<String, String>,
        @Query("type") type: String = "artist"
    ): SpotifyUserFollowingArtist

    @GET("me/top/tracks")
    suspend fun getCurrentUserTopTracks(
        @HeaderMap headers: Map<String, String>
    ): TrackList

    @GET("me/top/artists")
    suspend fun getCurrentUserTopArtists(
        @HeaderMap headers: Map<String, String>
    ): ArtistList

    @GET("me/playlists")
    suspend fun getCurrentUserPlaylists(
        @HeaderMap headers: Map<String, String>,
    ): SpotifyUserPlaylistsModel

    @GET("me/albums")
    suspend fun getCurrentUserAlbums(
        @HeaderMap headers: Map<String, String>,
    ): SpotifyLibraryAlbumModel

    @GET("me/shows")
    suspend fun getCurrentUserShows(
        @HeaderMap headers: Map<String, String>,
    ): SpotifyLibraryShowsModel

    @GET("me/episodes")
    suspend fun getCurrentUserEpisodes(
        @HeaderMap headers: Map<String, String>,
    ): SpotifyLibraryEpisodesModel

    @GET("me/tracks")
    suspend fun getCurrentUserTracks(
        @HeaderMap headers: Map<String, String>,
    ): SpotifyLibraryTracksModel

    @GET("me/player/recently-played")
    suspend fun getCurrentUserRecentlyPlayedTracks(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): SpotifyUserRecentlyPlayedModel

    @GET("users/{user_id}/playlists")
    suspend fun getUserPlaylists(
        @HeaderMap headers: Map<String, String>,
        @Path("user_id") userID: String
    ): SpotifyUserPlaylistsModel

    @GET("tracks/{track_id}")
    suspend fun getTrackDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("track_id") trackID: String
    ): Track

    @GET("albums/{album_id}")
    suspend fun getAlbumDetails(
        @HeaderMap headers: Map<String, String>,
        @Path("album_id") albumID: String
    ): Album

    @GET("search")
    suspend fun searchQuery(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): SpotifySearchModel

    @GET("recommendations")
    suspend fun getRecommendations(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): SpotifyRecommendationModel

    @GET("me/tracks")
    suspend fun getCurrentUserSavedSongs(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): SpotifyLikedSongsResponse
}