package fit.asta.health.navigation.track.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.exercise.ExerciseResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.menu.HomeMenuResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import okhttp3.OkHttpClient
import retrofit2.Response

class TrackingRestImpl(baseUrl: String, client: OkHttpClient) : TrackingApi {

    // Api Service Class Implementation
    private val trackingApiService: TrackingApiService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(TrackingApiService::class.java)

    override suspend fun getHomeDetails(
        uid: String,
        date: String,
        location: String
    ): Response<HomeMenuResponse> {
        return trackingApiService.getHomeDetails(uid = uid, date = date, location = location)
    }

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

    override suspend fun getMeditationDetails(
        uid: String,
        date: String,
        location: String,
        status: String
    ): Response<MeditationResponse> {

        return trackingApiService.getMeditationDetails(
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

        return trackingApiService.getBreathingDetails(
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

        return trackingApiService.getSleepDetails(
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

        return trackingApiService.getSunlightDetails(
            uid = uid,
            date = date,
            location = location,
            status = status
        )
    }

    override suspend fun getExerciseDetails(
        uid: String,
        date: String,
        location: String,
        exercise: String,
        status: String
    ): Response<ExerciseResponse> {

        return trackingApiService.getExerciseDetails(
            uid = uid,
            date = date,
            location = location,
            exercise = exercise,
            status = status
        )
    }

}