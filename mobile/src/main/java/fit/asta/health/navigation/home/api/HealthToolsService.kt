package fit.asta.health.navigation.home.api

import fit.asta.health.navigation.home.model.network.response.HealthTools
import retrofit2.http.GET
import retrofit2.http.Query


interface HealthToolsService {

    @GET("home/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String,
        @Query("end") endDate: String,
        @Query("time") time: String
    ): HealthTools
}