package fit.asta.health.data.walking.data.source.api

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Response
import fit.asta.health.common.utils.SubmitProfileResponse
import fit.asta.health.data.walking.data.source.network.request.PutData
import fit.asta.health.data.walking.data.source.network.request.PutDayData
import fit.asta.health.data.walking.data.source.network.response.HomeData
import retrofit2.http.*


//Health Tool - Walking Endpoints
interface WalkingApi {


    @GET("tools/walking/get/")
    suspend fun getHomeData(
        @Query("uid") userId: String
    ): Response<HomeData>


    @PUT("tools/walking/put/")
    suspend fun putData(@Body putData: PutData): Response<SubmitProfileResponse>

    @POST("tools/walking/day/post/")
    suspend fun putDayData(@Body putDayData: PutDayData): Response<SubmitProfileResponse>

    @GET("tools/health/list/get/?")
    suspend fun getSheetData(
        @Query("screenName") code: String
    ): Response<List<NetSheetData>>

    @GET("health/property/goals/get/all/?")
    suspend fun getSheetGoalsData(
        @Query("tool") tool: String
    ): Response<List<NetSheetData>>

}
