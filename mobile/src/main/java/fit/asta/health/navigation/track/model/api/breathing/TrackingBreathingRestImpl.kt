package fit.asta.health.navigation.track.model.api.breathing

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.track.model.network.breathing.NetBreathingRes
import okhttp3.OkHttpClient
import javax.inject.Inject

class TrackingBreathingRestImpl @Inject constructor(
    baseUrl: String, client: OkHttpClient
) : TrackingBreathingApi {

    private val apiService: TrackingBreathingApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(TrackingBreathingApiService::class.java)

    override suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetBreathingRes {
        return apiService.getDailyData(
            trackingType = "breath",
            uid = uid,
            date = date,
            location = location
        )
    }

    override suspend fun getWeeklyData() {
        TODO("Not yet implemented")
    }

    override suspend fun getMonthlyData() {
        TODO("Not yet implemented")
    }

    override suspend fun getYearlyData() {
        TODO("Not yet implemented")
    }
}