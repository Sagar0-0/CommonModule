package fit.asta.health.meditation.repo

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.meditation.remote.MeditationApi
import fit.asta.health.meditation.remote.network.PostRes
import fit.asta.health.meditation.remote.network.PutData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MeditationRepoImp(
    private val api: MeditationApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : MeditationRepo {
    override suspend fun getMeditationTool(
        uid: String,
        date: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getMeditationTool(uid, date) }
    }

    override suspend fun getMusicTool(uid: String) = withContext(coroutineDispatcher) {
        getApiResponseState { api.getMusicTool(uid) }
    }

    override suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>> =
        withContext(coroutineDispatcher) {
            getApiResponseState { api.getSheetData(code) }
        }

    override suspend fun putMeditationData(putData: PutData) = withContext(coroutineDispatcher) {
        getApiResponseState { api.putMeditationData(putData) }
    }

    override suspend fun postMeditationData(postData: PostRes) = withContext(coroutineDispatcher) {
        getApiResponseState { api.postMeditationData(postData) }
    }
}