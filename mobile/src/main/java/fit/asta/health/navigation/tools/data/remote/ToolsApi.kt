package fit.asta.health.navigation.tools.data.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.navigation.tools.data.remote.model.ToolsHome
import retrofit2.http.*

//Tools Endpoints
interface ToolsApi {

    @GET("tool/home/get/?")
    suspend fun getHomeData(
        @Query("uid") userId: String,
        @Query("lat") latitude: String,
        @Query("lon") longitude: String,
        @Query("loc") location: String,
        @Query("start") startDate: String
    ): Response<ToolsHome>
}