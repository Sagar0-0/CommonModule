package fit.asta.health.navigation.track.model.api.water

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.track.model.network.water.NetWaterDailyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterMonthlyRes
import fit.asta.health.navigation.track.model.network.water.NetWaterWeeklyRes
import okhttp3.OkHttpClient
import javax.inject.Inject

class TrackingWaterRestImpl @Inject constructor(
    baseUrl: String, client: OkHttpClient
) : TrackingWaterApi {

    private val apiService: TrackingWaterApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(TrackingWaterApiService::class.java)

    override suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetWaterDailyRes {
        return apiService.getDailyData(
            trackingType = "water",
            uid = uid,
            date = date,
            location = location
        )
    }

    override suspend fun getWeeklyData(
        uid: String,
        date: String
    ): NetWaterWeeklyRes {
        return apiService.getWeeklyData(
            trackingType = "water",
            uid = uid,
            date = date
        )
    }

    override suspend fun getMonthlyData(
        uid: String,
        month: String
    ): NetWaterMonthlyRes {
        return apiService.getMonthlyData(
            trackingType = "water",
            uid = uid,
            month = month
        )
    }

    override suspend fun getYearlyData() {
        TODO("Not yet implemented")
    }
}