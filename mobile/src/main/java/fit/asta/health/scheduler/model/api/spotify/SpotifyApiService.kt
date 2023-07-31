package fit.asta.health.scheduler.model.api.spotify

import fit.asta.health.scheduler.model.net.spotify.search.SpotifySearchModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import fit.asta.health.scheduler.model.net.spotify.me.SpotifyMeModel
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import retrofit2.Response
import retrofit2.http.*

interface SpotifyApiService {

    @GET("me")
    suspend fun getCurrentUserDetails(
        @HeaderMap headers: Map<String, String>
    ): Response<SpotifyMeModel>

    @GET("me/top/tracks")
    suspend fun getCurrentUserTopTracks(
        @HeaderMap headers: Map<String, String>
    ): Response<TrackList>

    @GET("me/player/recently-played")
    suspend fun getCurrentUserRecentlyPlayedTracks(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifyUserRecentlyPlayedModel>

    @GET("search")
    suspend fun searchQuery(
        @HeaderMap headers: Map<String, String>,
        @QueryMap queries: Map<String, String>
    ): Response<SpotifySearchModel>
}