package fit.asta.health.sunlight.repo

import com.hoho.assignmentsun.model.SunDetailsResponseDTO
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.api.emitResponse
import fit.asta.health.sunlight.remote.SunlightApi
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SunlightRepoImpl
@Inject constructor(
    private val remoteApi: SunlightApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SunlightRepo {
    override  fun getSunData(
        date: String,
        lat: Double,
        lang: Double
    ): Flow<ResponseState<SunDetailsResponseDTO>> = flow {
        emit(ResponseState.NoInternet)
    }

    override  fun getScreenContentList(name: String): Flow<ResponseState<ArrayList<SkinConditionResponseData>>> =
        flow {
            emitResponse {
                remoteApi.getScreenContentList(name)
            }
        }.flowOn(coroutineDispatcher)

    override fun putSkinConditionData(name: SkinConditionBody): Flow<ResponseState<PutResponse>>  =
        flow {
            emitResponse {
                remoteApi.updateSkinConditionData(name)
            }
        }.flowOn(coroutineDispatcher)
}