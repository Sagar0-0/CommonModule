package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.sunlight.api.emitResponse
import fit.asta.health.sunlight.remote.SunlightApi
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SunlightHomeRepoImpl
@Inject constructor(
    private val remoteApi: SunlightApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SunlightHomeRepo {
    override fun getSunlightHomeData(
        uid: String,
        lat: String,
        lon: String,
        date: Long,
        loc: String
    ): Flow<ResponseState<SunlightHomeData>> = flow {
        emitResponse {
            remoteApi.getSunlightHomeScreen(
                uid, lat, lon, date, loc
            )
        }
    }.flowOn(coroutineDispatcher)

    override fun getSupplementAndFoodInfo(): Flow<ResponseState<HelpAndNutrition>> = flow {
        emitResponse {
            remoteApi.getSupplementAndFoodInfo()
        }
    }.flowOn(coroutineDispatcher)

    override fun getSunlightSessionData(
        data: SessionDetailBody
    ): Flow<ResponseState<SunlightSessionData>> = flow {
        emitResponse {
            remoteApi.getSessionDetail(
                data
            )
        }
    }.flowOn(coroutineDispatcher)

}