package fit.asta.health.navigation.track.data.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.navigation.track.data.remote.model.breathing.BreathingResponse
import fit.asta.health.navigation.track.data.remote.model.exercise.ExerciseResponse
import fit.asta.health.navigation.track.data.remote.model.meditation.MeditationResponse
import fit.asta.health.navigation.track.data.remote.model.menu.HomeMenuResponse
import fit.asta.health.navigation.track.data.remote.model.sleep.SleepResponse
import fit.asta.health.navigation.track.data.remote.model.step.StepsResponse
import fit.asta.health.navigation.track.data.remote.model.sunlight.SunlightResponse
import fit.asta.health.navigation.track.data.remote.model.water.WaterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackingApiService {

    @GET("tracking/home/get/")
    suspend fun getHomeDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<HomeMenuResponse>

    @GET("tracking/water/get/")
    suspend fun getWaterDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<WaterResponse>

    @GET("tracking/steps/get/")
    suspend fun getStepsDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<StepsResponse>

    @GET("tracking/meditation/get/")
    suspend fun getMeditationDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<MeditationResponse>

    @GET("tracking/breathing/get/")
    suspend fun getBreathingDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<BreathingResponse>

    @GET("tracking/sleep/get/")
    suspend fun getSleepDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<SleepResponse>

    @GET("tracking/sunlight/get/")
    suspend fun getSunlightDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("sts") status: String
    ): Response<SunlightResponse>

    @GET("tracking/exercise/get/")
    suspend fun getExerciseDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String,
        @Query("exe") exercise: String,
        @Query("sts") status: String
    ): Response<ExerciseResponse>
}