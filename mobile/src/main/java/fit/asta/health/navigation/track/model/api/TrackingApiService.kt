package fit.asta.health.navigation.track.model.api

import fit.asta.health.navigation.track.model.net.meditation.MeditationResponse
import fit.asta.health.navigation.track.model.net.step.StepsResponse
import fit.asta.health.navigation.track.model.net.water.WaterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TrackingApiService {

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
}