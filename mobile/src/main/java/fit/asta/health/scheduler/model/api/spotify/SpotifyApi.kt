package fit.asta.health.scheduler.model.api.spotify


import fit.asta.health.scheduler.model.net.spotify.me.SpotifyMeModel
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import fit.asta.health.scheduler.model.net.spotify.search.SpotifySearchModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import retrofit2.Response

interface SpotifyApi {

    suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel>

    suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackList>

    suspend fun getCurrentUserRecentlyPlayedTracks(accessToken: String): Response<SpotifyUserRecentlyPlayedModel>

    suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModel>
}