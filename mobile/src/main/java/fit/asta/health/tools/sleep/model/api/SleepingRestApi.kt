package fit.asta.health.tools.sleep.model.api

import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.goals.SleepGoalResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import okhttp3.OkHttpClient
import retrofit2.Response

class SleepingRestApi(baseUrl: String, client: OkHttpClient) : SleepingApi {

    private val apiService: SleepingService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SleepingService::class.java)

    override suspend fun putUserData(
        toolData: ToolData
    ): Response<SleepPutResponse> {
        return apiService.putUserData(toolData)
    }

    override suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): Response<SleepToolGetResponse> {
        return apiService.getUserDefaultSettings(userId, date)
    }

    override suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ): Response<SleepPutResponse> {
        return apiService.postUserReading(userId, sleepPostRequestBody)
    }

    override suspend fun getSleepPropertyData(
        userId: String,
        property: String
    ): Response<SleepDisturbanceResponse> {
        return apiService.getPropertyData(userId, property)
    }

    override suspend fun getJetLagTips(id: String): Response<SleepJetLagTipResponse> {
        return apiService.getJetLagTips(id = id)
    }

    override suspend fun getGoalsList(property: String): Response<SleepGoalResponse> {
        return apiService.getGoalsList(property = property)
    }
}