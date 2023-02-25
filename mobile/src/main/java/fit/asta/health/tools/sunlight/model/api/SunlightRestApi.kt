package fit.asta.health.tools.sunlight.model.api

import fit.asta.health.tools.sunlight.model.network.response.NetSunlightToolRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient


//Health Tool - Water Endpoints
class SunlightRestApi(baseUrl: String, client: OkHttpClient) :
    SunlightApi {

    private val apiService: SunlightService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SunlightService::class.java)

    override suspend fun getSunlightTool(userId: String): NetSunlightToolRes {
        return apiService.getSunlightTool(userId)
    }
}