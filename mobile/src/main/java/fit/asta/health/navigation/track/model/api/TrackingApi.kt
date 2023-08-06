package fit.asta.health.navigation.track.model.api

import fit.asta.health.navigation.track.model.net.water.WaterResponse
import retrofit2.Response

interface TrackingApi {

    suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<WaterResponse>

}