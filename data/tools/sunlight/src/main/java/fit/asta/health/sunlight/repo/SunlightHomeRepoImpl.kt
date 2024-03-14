package fit.asta.health.sunlight.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.sunlight.remote.SunlightApi
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SunlightHomeRepoImpl
@Inject constructor(
    private val remoteApi: SunlightApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SunlightHomeRepo {
    override suspend fun getSunlightHomeData(
        uid: String,
        lat: String,
        lon: String,
        date: Long,
        loc: String
    ): ResponseState<SunlightHomeData> = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getSunlightHomeScreen(
                uid, lat, lon, date, loc
            )
        }
    }

    override suspend fun getSupplementAndFoodInfo(): ResponseState<HelpAndNutrition> =
        withContext(coroutineDispatcher) {
            getApiResponseState {
                remoteApi.getSupplementAndFoodInfo()
            }
        }

    override suspend fun getSunlightSessionData(
        data: SessionDetailBody
    ): ResponseState<SunlightSessionData> = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getSessionDetail(
                data
            )
        }
    }
}