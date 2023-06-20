package fit.asta.health.navigation.track.model.api.water

import fit.asta.health.navigation.track.model.network.water.NetWaterRes

interface TrackingWaterApi {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetWaterRes

    suspend fun getWeeklyData()
    suspend fun getMonthlyData()
    suspend fun getYearlyData()

}