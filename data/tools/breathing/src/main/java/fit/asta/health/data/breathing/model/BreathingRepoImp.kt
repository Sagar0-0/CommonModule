package fit.asta.health.data.breathing.model

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.breathing.model.api.BreathingApi
import fit.asta.health.data.breathing.model.network.CustomRatioData
import fit.asta.health.data.breathing.model.network.NetGetStart
import fit.asta.health.data.breathing.model.network.request.NetPost
import fit.asta.health.data.breathing.model.network.request.NetPut
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreathingRepoImp(
    val api: BreathingApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BreathingRepo {
    override suspend fun getBreathingTool(userId: String, date: Long) =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getBreathingTool(userId, date) }
        }

    override suspend fun getAllBreathingData(userId: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getAllBreathingData(userId) }
    }

    override suspend fun getStart(userId: String): ResponseState<NetGetStart> =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getStart(userId) }
        }

    override suspend fun putBreathingData(netPut: NetPut) = withContext(coroutineDispatcher) {
        getApiResponseState { api.putBreathingData(netPut) }
    }

    override suspend fun postBreathingData(netPost: NetPost) = withContext(coroutineDispatcher) {
        getApiResponseState { api.postBreathingData(netPost) }
    }

    override suspend fun postRatioData(customRatioData: CustomRatioData) =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.postRatioData(customRatioData) }
        }

    override suspend fun deleteRatioData(ratioId: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.deleteRatioData(ratioId) }
    }

    override suspend fun getSheetData(code: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getSheetData(code) }
    }

}