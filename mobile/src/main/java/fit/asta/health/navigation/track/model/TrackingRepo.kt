package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import retrofit2.Response

interface TrackingRepo {

    suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<WaterResponse>

    suspend fun getStepsDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<StepsResponse>
}