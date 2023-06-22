package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.api.breathing.TrackingBreathingApi
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TrackingBreathingRepoImpl @Inject constructor(
    private val remoteApi: TrackingBreathingApi
) : TrackingBreathingRepo {

    override suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): Flow<NetBreathingRes> {

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