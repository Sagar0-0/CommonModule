package fit.asta.health.navigation.track.model

import android.util.Log.d
import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.track.model.api.TrackingApiService
import fit.asta.health.navigation.track.model.network.NetBreathingRes
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient

class TrackingRepoImpl(
    baseUrl: String,
    client: OkHttpClient
) : TrackingRepo {

    private val apiInstance: TrackingApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(TrackingApiService::class.java)

    override suspend fun getDailyData(
        uid: String,
        date: String,
        location: String
    ): NetBreathingRes {

        val response = apiInstance.getDailyData(
            trackingType = "breath",
            uid = uid,
            date = date,
            location = location
        )

        if (response.isSuccessful) {
            d("Got Response", response.body()!!.toString())
        }

        return response.body()!!
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