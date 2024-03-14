package fit.asta.health.data.walking.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.walking.remote.WalkingApi
import fit.asta.health.data.walking.remote.model.PutData
import fit.asta.health.data.walking.remote.model.PutDayData
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class WalkingToolRepoImpl
@Inject constructor(
    private val api: WalkingApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
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
            getApiResponseState { api.getSheetListData(code) }
        }

    override suspend fun getSheetGoalsData(tool: String): ResponseState<List<NetSheetData>> =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getSheetGoalsListData(tool) }
        }
}