package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import fit.asta.health.navigation.track.model.network.water.NetWaterRes
import kotlinx.coroutines.flow.Flow

interface TrackingWaterRepo {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): Flow<NetWaterRes>

    suspend fun getWeeklyData(): Flow<NetBreathingRes>

    suspend fun getMonthlyData(): Flow<NetBreathingRes>

    suspend fun getYearlyData(): Flow<NetBreathingRes>
}