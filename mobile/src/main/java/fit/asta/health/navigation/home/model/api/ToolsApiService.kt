package fit.asta.health.navigation.home.model.api

import fit.asta.health.navigation.home.model.domain.ToolsHomeRes
import retrofit2.http.*

//Tools Endpoints
interface ToolsApiService {

    @GET("tool/screen/get?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String
    ): ToolsHomeRes
}