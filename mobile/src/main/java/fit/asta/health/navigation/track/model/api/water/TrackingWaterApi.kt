package fit.asta.health.navigation.track.model.api.water

import fit.asta.health.navigation.track.model.network.water.NetWaterRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes

interface TrackingWaterApi {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetWaterRes

    suspend fun getWeeklyData(
        uid: String,
        date: String
    ): NetWaterWeeklyRes

    suspend fun getMonthlyData()
    suspend fun getYearlyData()

}