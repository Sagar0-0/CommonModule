package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.api.water.TrackingWaterApi
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import fit.asta.health.navigation.track.model.network.water.NetWaterRes
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
    ): Flow<NetWaterRes> {

        return flow {
            emit(
                remoteApi.getDailyData(uid = uid, date = date, location = location)
            )
        }
    }

    override suspend fun getWeeklyData(): Flow<NetBreathingRes> {
        TODO("Not yet implemented")
    }

    override suspend fun getMonthlyData(): Flow<NetBreathingRes> {
        TODO("Not yet implemented")
    }

    override suspend fun getYearlyData(): Flow<NetBreathingRes> {
        TODO("Not yet implemented")
    }
}