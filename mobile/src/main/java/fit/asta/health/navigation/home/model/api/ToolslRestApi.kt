package fit.asta.health.navigation.home.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import okhttp3.OkHttpClient


//Tools Endpoints
class ToolsRestApi(baseUrl: String, client: OkHttpClient) : ToolsApi {

    private val apiService: ToolsApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(ToolsApiService::class.java)

    override suspend fun getHomeData(
        userId: String,
        latitude: String,
        longitude: String,
        location: String,
        startDate: String
    ): ToolsHomeRes {
        return apiService.getHomeData(
            userId,
            latitude,
            longitude,
            location,
            startDate
        )
    }
}