package fit.asta.health.data.walking.data.repository

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.walking.data.source.api.WalkingApi
import fit.asta.health.data.walking.data.source.network.request.PutData
import fit.asta.health.data.walking.data.source.network.request.PutDayData
import fit.asta.health.data.walking.domain.repository.WalkingToolRepo
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext


class WalkingToolRepoImpl(
    private val api: WalkingApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val prefManager: PrefManager
) : WalkingToolRepo {
    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override suspend fun updateStepsPermissionRejectedCount(newValue: Int) {
        prefManager.setStepsPermissionRejectedCount(newValue)
    }

    override suspend fun getHomeData(userId: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getHomeData(userId) }
    }

    override suspend fun putData(putData: PutData) = withContext(coroutineDispatcher) {
        getApiResponseState { api.putData(putData) }
    }

    override suspend fun putDayData(putDayData: PutDayData) = withContext(coroutineDispatcher) {
        getApiResponseState { api.putDayData(putDayData) }
    }

    override suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>> =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getSheetData(code) }
        }

    override suspend fun getSheetGoalsData(tool: String): ResponseState<List<NetSheetData>> =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getSheetGoalsData(tool) }
        }
}