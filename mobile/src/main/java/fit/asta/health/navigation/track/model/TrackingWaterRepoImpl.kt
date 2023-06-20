package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.api.water.TrackingWaterApi
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import fit.asta.health.navigation.track.model.network.water.NetWaterDailyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterMonthlyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrackingWaterRepoImpl @Inject constructor(
    private val remoteApi: TrackingWaterApi
) : TrackingWaterRepo {
    override suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): Flow<NetWaterDailyRes> {

        return flow {
            emit(
                remoteApi.getDailyData(uid = uid, date = date, location = location)
            )
        }
    }

    override suspend fun getWeeklyData(
        uid: String,
        date: String
    ): Flow<NetWaterWeeklyRes> {

        return flow {
            emit(
                remoteApi.getWeeklyData(uid = uid, date = date)
            )
        }
    }

    override suspend fun getMonthlyData(
        uid: String,
        month: String
    ): Flow<NetWaterMonthlyRes> {
        return flow {
            emit(
                remoteApi.getMonthlyData(uid = uid, month = month)
            )
        }
    }


    override suspend fun getYearlyData(): Flow<NetBreathingRes> {
        TODO("Not yet implemented")
    }
}