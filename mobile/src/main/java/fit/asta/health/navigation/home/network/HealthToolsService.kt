package fit.asta.health.navigation.home.network

import fit.asta.health.navigation.home.network.response.HealthToolsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface HealthToolsService {

    @GET("home/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("wid") wid: String
    ): HealthToolsResponse
}