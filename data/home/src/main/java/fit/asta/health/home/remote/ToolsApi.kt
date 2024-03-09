package fit.asta.health.home.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.home.remote.model.ToolsHome
import retrofit2.http.GET
import retrofit2.http.Query

//Tools Endpoints
interface ToolsApi {

    @GET("tool/home/get/?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: Long
    ): Response<ToolsHome>
}