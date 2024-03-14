package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.sunlight.remote.SunlightApi
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SunlightRepoImpl
@Inject constructor(
    private val remoteApi: SunlightApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SunlightRepo {
    override suspend fun getScreenContentList(name: String): ResponseState<ArrayList<SkinConditionResponseData>> =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getScreenContentList(name)
            }
        }

    override suspend fun putSkinConditionData(name: SkinConditionBody): ResponseState<PutResponse> =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.updateSkinConditionData(name)
            }
        }
}