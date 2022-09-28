package fit.asta.health.navigation.home.api

import fit.asta.health.navigation.home.model.network.response.HealthToolsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface HealthToolsService {

    @GET("home/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("wid") wid: String
    ): HealthToolsResponse
}