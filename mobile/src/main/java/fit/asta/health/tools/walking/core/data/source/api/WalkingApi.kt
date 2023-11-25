package fit.asta.health.tools.walking.core.data.source.api

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.tools.walking.core.data.source.network.request.PutData
import fit.asta.health.tools.walking.core.data.source.network.request.PutDayData
import fit.asta.health.tools.walking.core.data.source.network.response.HomeData
import retrofit2.http.*


//Health Tool - Walking Endpoints
interface WalkingApi {


    @GET("tools/walking/get/")
    suspend fun getHomeData(
        @Query("uid") userId: String
    ): Response<HomeData>


    @PUT("tools/walking/put/")
    suspend fun putData(@Body putData: PutData): Response<PutResponse>

    @POST("tools/walking/day/post/")
    suspend fun putDayData(@Body putDayData: PutDayData): Response<PutResponse>

    @GET("tools/health/list/get/?")
    suspend fun getSheetData(
        @Query("screenName") code: String
    ): Response<List<NetSheetData>>

    @GET("health/property/goals/get/all/?")
    suspend fun getSheetGoalsData(
        @Query("tool") tool: String
    ): Response<List<NetSheetData>>

}
