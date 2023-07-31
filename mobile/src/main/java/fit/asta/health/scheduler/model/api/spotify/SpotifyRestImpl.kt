package fit.asta.health.scheduler.model.api.spotify

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.scheduler.model.net.spotify.search.SpotifySearchModel
import fit.asta.health.scheduler.model.net.spotify.search.TrackList
import fit.asta.health.scheduler.model.net.spotify.me.SpotifyMeModel
import fit.asta.health.scheduler.model.net.spotify.recently.SpotifyUserRecentlyPlayedModel
import okhttp3.OkHttpClient
import retrofit2.Response
import javax.inject.Inject

class SpotifyRestImpl @Inject constructor(baseUrl: String, client: OkHttpClient) : SpotifyApi {

    private val spotifyApiService: SpotifyApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SpotifyApiService::class.java)

    override suspend fun getCurrentUserDetails(accessToken: String): Response<SpotifyMeModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserDetails(headerMap)
    }

    override suspend fun getCurrentUserTopTracks(accessToken: String): Response<TrackList> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        return spotifyApiService.getCurrentUserTopTracks(headerMap)
    }

    override suspend fun getCurrentUserRecentlyPlayedTracks(
        accessToken: String
    ): Response<SpotifyUserRecentlyPlayedModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        return spotifyApiService.getCurrentUserRecentlyPlayedTracks(headerMap, queryMap)
    }

    override suspend fun searchQuery(
        accessToken: String,
        query: String,
        type: String,
        includeExternal: String,
        market: String
    ): Response<SpotifySearchModel> {
        val headerMap: HashMap<String, String> = HashMap()
        headerMap["Authorization"] = "Bearer $accessToken"
        headerMap["Content-Type"] = "application/json"
        val queryMap: HashMap<String, String> = HashMap()
        queryMap["q"] = query
        queryMap["type"] = type
        queryMap["include_external"] = includeExternal
        queryMap["market"] = market
        return spotifyApiService.searchQuery(headerMap, queryMap)
    }
}