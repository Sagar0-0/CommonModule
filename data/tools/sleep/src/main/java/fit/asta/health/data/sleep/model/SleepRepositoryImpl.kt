package fit.asta.health.data.sleep.model

import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.sleep.model.api.SleepingApi
import fit.asta.health.data.sleep.model.network.common.ToolData
import fit.asta.health.data.sleep.model.network.post.SleepPostRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SleepRepositoryImpl(
    private val api: SleepingApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
) : SleepRepository {
    override suspend fun putUserData(toolData: ToolData) = withContext(coroutineDispatcher) {
        getApiResponseState { api.putUserData(toolData) }
    }

    override suspend fun getUserDefaultSettings(
        userId: String,
        date: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getUserDefaultSettings(userId, date) }
    }

    override suspend fun postUserReading(
        userId: String,
        sleepPostRequestBody: SleepPostRequestBody
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { api.postUserReading(userId, sleepPostRequestBody) }
    }

    override suspend fun getPropertyData(
        userId: String,
        property: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getPropertyData(userId, property) }
    }

    override suspend fun getJetLagTips(id: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getJetLagTips(id) }
    }

    override suspend fun getGoalsList(property: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getGoalsList(property) }
    }
}