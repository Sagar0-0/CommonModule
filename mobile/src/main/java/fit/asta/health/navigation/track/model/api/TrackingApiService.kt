package fit.asta.health.navigation.track.model.api

import fit.asta.health.navigation.track.model.network.NetBreathingRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrackingApiService {

    @GET("tracking/{trackingType}/daily/get/")
    suspend fun getDailyData(
        @Path("trackingType") trackingType: String,
        @Query("uid") uid: String,
        @Query("date") date: String,
        @Query("loc") location: String
    ): Response<NetBreathingRes>

}