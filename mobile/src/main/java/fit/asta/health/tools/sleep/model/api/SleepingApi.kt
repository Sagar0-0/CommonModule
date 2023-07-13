package fit.asta.health.tools.sleep.model.api

import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response

interface SleepingApi {

    suspend fun putUserDataForFirstTimeUsers(
        sleepPutRequestBody: SleepPutRequestBody
    ): Response<SleepPutResponse>

    suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): Response<SleepToolGetResponse>

    suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ): Response<SleepPutResponse>

}