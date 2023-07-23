package fit.asta.health.tools.sleep.model.api

import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.goals.SleepGoalResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response

interface SleepingApi {

    suspend fun putUserData(
        toolData: ToolData
    ): Response<SleepPutResponse>

    suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): Response<SleepToolGetResponse>

    suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ): Response<SleepPutResponse>

    suspend fun getSleepPropertyData(
        userId: String,
        property: String
    ): Response<SleepDisturbanceResponse>

    suspend fun getJetLagTips(id: String): Response<SleepJetLagTipResponse>

    suspend fun getGoalsList(property: String): Response<SleepGoalResponse>
}