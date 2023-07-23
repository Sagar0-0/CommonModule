package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response

interface SleepRepository {

    suspend fun putUserData(
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

    suspend fun getPropertyData(
        userId: String,
        property: String
    ): Response<SleepDisturbanceResponse>

    suspend fun getJetLagTips(id: String): Response<SleepJetLagTipResponse>
}