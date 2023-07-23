package fit.asta.health.tools.sleep.model

import fit.asta.health.tools.sleep.model.api.SleepingApi
import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response

class SleepRepositoryImpl(private val api: SleepingApi) : SleepRepository {

    override suspend fun putUserData(
        toolData: ToolData
    ): Response<SleepPutResponse> {
        return api.putUserData(toolData = toolData)
    }

    override suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ): Response<SleepToolGetResponse> {
        return api.getUserDefaultSettings(userId = userId, date = date)
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

    override suspend fun getPropertyData(
        userId: String,
        property: String
    ): Response<SleepDisturbanceResponse> {
        return api.getSleepPropertyData(userId, property)
    }

    override suspend fun getJetLagTips(id: String): Response<SleepJetLagTipResponse> {
        return api.getJetLagTips(id)
    }
}