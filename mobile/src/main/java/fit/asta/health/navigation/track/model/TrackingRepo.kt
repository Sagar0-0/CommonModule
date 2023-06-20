package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.network.NetBreathingRes
import kotlinx.coroutines.flow.Flow


interface TrackingRepo {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetBreathingRes

    suspend fun getWeeklyData(): Flow<NetBreathingRes>

    suspend fun getMonthlyData(): Flow<NetBreathingRes>

    suspend fun getYearlyData(): Flow<NetBreathingRes>
}