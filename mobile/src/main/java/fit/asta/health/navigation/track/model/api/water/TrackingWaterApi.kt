package fit.asta.health.navigation.track.model.api.water

import fit.asta.health.navigation.track.model.network.water.NetWaterDailyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterMonthlyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes

interface TrackingWaterApi {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetWaterDailyRes

    suspend fun getWeeklyData(
        uid: String,
        date: String
    ): NetWaterWeeklyRes

    suspend fun getMonthlyData(
        uid: String,
        month: String
    ): NetWaterMonthlyRes

    suspend fun getYearlyData()

}