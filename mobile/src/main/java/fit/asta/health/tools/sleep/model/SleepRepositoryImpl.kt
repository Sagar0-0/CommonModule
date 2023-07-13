package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.api.SleepingApi
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response

class SleepRepositoryImpl(private val api: SleepingApi) : SleepRepository {

    override suspend fun putUserDataForFirstTimeUsers(
        sleepPutRequestBody: SleepPutRequestBody
    ): Response<SleepPutResponse> {
        return api.putUserDataForFirstTimeUsers(sleepPutRequestBody = sleepPutRequestBody)
    }

    override suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): Response<SleepToolGetResponse> {
        return api.getUserDefaultSettings(
            userId = userId,
            date = date
        )
    }

    override suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ): Response<SleepPutResponse> {
        return api.postUserReading(
            userId = userId,
            sleepPostRequestBody = sleepPostRequestBody
        )
    }
}