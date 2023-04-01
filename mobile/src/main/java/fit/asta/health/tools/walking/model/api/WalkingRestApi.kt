package fit.asta.health.tools.walking.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.tools.walking.model.network.request.PutData
import fit.asta.health.tools.walking.model.network.request.PutDayData
import fit.asta.health.tools.walking.model.network.response.HomeData
import fit.asta.health.tools.walking.model.network.response.PutResponse
import okhttp3.OkHttpClient


//Health Tool - Walking Endpoints
class WalkingRestApi(baseUrl: String, client: OkHttpClient) :
    WalkingApi {

    private val apiService: WalkingService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(WalkingService::class.java)

    override suspend fun getHomeData(userId: String): HomeData {
        return apiService.getHomeData(userId)
    }

    override suspend fun putData(putData: PutData): PutResponse {
        return apiService.putData(putData)
    }
    override suspend fun putDayData(putDayData: PutDayData): PutResponse {
        return apiService.putDayData(putDayData)
    }
}