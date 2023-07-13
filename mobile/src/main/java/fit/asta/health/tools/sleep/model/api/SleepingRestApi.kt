package fit.asta.health.tools.sleep.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import okhttp3.OkHttpClient
import retrofit2.Response

class SleepingRestApi(baseUrl: String, client: OkHttpClient) : SleepingApi {

    private val apiService: SleepingService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(SleepingService::class.java)

    override suspend fun putUserDataForFirstTimeUsers(
        sleepPutRequestBody: SleepPutRequestBody
    ): Response<SleepPutResponse> {
        return apiService.putUserDataForFirstTimeUsers(sleepPutRequestBody)
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

}