package fit.asta.health.navigation.track.model

import fit.asta.health.navigation.track.model.api.TrackingApi
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import retrofit2.Response
import javax.inject.Inject

class TrackingRepoImpl @Inject constructor(
    private val trackingApi: TrackingApi
) : TrackingRepo {

    override suspend fun getWaterDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<WaterResponse> {

        return trackingApi.getWaterDetails(
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

        return trackingApi.getStepsDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getMeditationDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<MeditationResponse> {

        return trackingApi.getMeditationDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getBreathingDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<BreathingResponse> {

        return trackingApi.getBreathingDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getSleepDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<SleepResponse> {

        return trackingApi.getSleepDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getSunlightDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<SunlightResponse> {

        return trackingApi.getSunlightDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }
}