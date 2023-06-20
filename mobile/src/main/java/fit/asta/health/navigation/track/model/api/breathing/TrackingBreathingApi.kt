package fit.asta.health.navigation.track.model.api.breathing

import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes

interface TrackingBreathingApi {

    suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetBreathingRes

    suspend fun getWeeklyData()
    suspend fun getMonthlyData()
    suspend fun getYearlyData()

}