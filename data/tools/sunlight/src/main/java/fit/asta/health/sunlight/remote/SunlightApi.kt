package fit.asta.health.sunlight.remote

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.sunlight.remote.model.HelpAndNutrition
import fit.asta.health.sunlight.remote.model.SessionDetailBody
import fit.asta.health.sunlight.remote.model.SkinConditionBody
import fit.asta.health.sunlight.remote.model.SkinConditionResponseData
import fit.asta.health.sunlight.remote.model.SunlightHomeData
import fit.asta.health.sunlight.remote.model.SunlightSessionData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SunlightApi {
    @GET("/tools/health/list/get/")
    suspend fun getScreenContentList(@Query("screenName") name: String): Response<ArrayList<SkinConditionResponseData>>

    @PUT("/tools/sunlight/put/")
    suspend fun updateSkinConditionData(@Body name: SkinConditionBody): Response<PutResponse>

    @GET("/tools/sunlight/get/")
    suspend fun getSunlightHomeScreen(
        @Query("uid") uid: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("date") date: Long,
        @Query("loc") loc: String,
    ): Response<SunlightHomeData>

    @GET("/tools/sunlight/supplement/get/")
    suspend fun getSupplementAndFoodInfo(): Response<HelpAndNutrition>

    @POST("/tools/sunlight/activity/post/")
    suspend fun getSessionDetail(
       @Body sessionDetailBody:SessionDetailBody
    ): Response<SunlightSessionData>
}
