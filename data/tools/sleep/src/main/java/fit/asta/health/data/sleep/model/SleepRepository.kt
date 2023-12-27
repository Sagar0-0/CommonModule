package fit.asta.health.data.sleep.model

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.sleep.model.network.common.ToolData
import fit.asta.health.data.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.data.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.data.sleep.model.network.goals.SleepGoalData
import fit.asta.health.data.sleep.model.network.jetlag.JetLagTipsData
import fit.asta.health.data.sleep.model.network.post.SleepPostRequestBody

interface SleepRepository {

    suspend fun putUserData(
        toolData: ToolData
    ): ResponseState<PutResponse>

    suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): ResponseState<SleepToolGetResponse>

    suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ): ResponseState<PutResponse>

    suspend fun getPropertyData(
        userId: String,
        property: String
    ): ResponseState<SleepDisturbanceResponse>

    suspend fun getJetLagTips(id: String): ResponseState<JetLagTipsData>

    suspend fun getGoalsList(property: String): ResponseState<List<SleepGoalData>>
}