package fit.asta.health.navigation.track.model.api

import fit.asta.health.navigation.track.model.net.breathing.BreathingResponse
import fit.asta.health.navigation.track.model.net.exercise.ExerciseResponse
import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.menu.HomeMenuResponse
import fit.asta.health.navigation.track.model.net.sleep.SleepResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.sunlight.SunlightResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackingApiService {

    @GET("tracking/home/get/")
    suspend fun getHomeDetails(
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String
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