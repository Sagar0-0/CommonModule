package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.response.NetWalkingToolRes
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient


//Health Tool - Walking Endpoints
class WalkingRestApi(baseUrl: String, client: OkHttpClient) :
    WalkingApi {

    private val apiService: WalkingService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(WalkingService::class.java)

    override suspend fun getWalkingTool(userId: String): NetWalkingToolRes {
        return apiService.getWalkingTool(userId)
    }
}