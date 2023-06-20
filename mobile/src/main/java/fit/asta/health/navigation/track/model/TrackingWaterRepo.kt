package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import fit.asta.health.navigation.track.model.network.water.NetWaterRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes
import kotlinx.coroutines.flow.Flow

interface TrackingWaterRepo {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): Flow<NetWaterRes>

    suspend fun getWeeklyData(
        uid: String,
        date: String,
    ): Flow<NetWaterWeeklyRes>

    suspend fun getMonthlyData(): Flow<NetBreathingRes>

    suspend fun getYearlyData(): Flow<NetBreathingRes>
}