package fit.asta.health.navigation.track.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import okhttp3.OkHttpClient
import retrofit2.Response

class TrackingRestImpl(baseUrl: String, client: OkHttpClient) : TrackingApi {

    // Api Service Class Implementation
    private val trackingApiService: TrackingApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(TrackingApiService::class.java)

    override suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<WaterResponse> {

        return trackingApiService.getWaterDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getStepsDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<StepsResponse> {

        return trackingApiService.getStepsDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

}