package fit.asta.health.data.sleep.model.api

import fit.asta.health.common.utils.PutResponse
import fit.asta.health.common.utils.Response
import fit.asta.health.data.sleep.model.network.common.ToolData
import fit.asta.health.data.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.data.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.data.sleep.model.network.goals.SleepGoalData
import fit.asta.health.data.sleep.model.network.jetlag.JetLagTipsData
import fit.asta.health.data.sleep.model.network.post.SleepPostRequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SleepingApi {

    @PUT("tools/sleep/put/")
    suspend fun putUserData(
        @Body toolData: ToolData
    ): Response<PutResponse>

    @GET("tools/sleep/get/")
    suspend fun getUserDefaultSettings(
        @Query("uid") userId: String,
        @Query("date") date: String
    ): Response<SleepToolGetResponse>

    @POST("tools/sleep/activity/post/")
    suspend fun postUserReading(
        @Query("uid") userId: String,
        @Body sleepPostRequestBody: SleepPostRequestBody
    ): Response<PutResponse>

    @GET("tools/sleep/property/list/get/")
    suspend fun getPropertyData(
        @Query("uid") userId: String,
        @Query("property") property: String
    ): Response<SleepDisturbanceResponse>

    @GET("tools/help/get/")
    suspend fun getJetLagTips(
        @Query("id") id: String
    ): Response<JetLagTipsData>

    @GET("health/property/get/all/")
    suspend fun getGoalsList(
        @Query("property") property: String
    ): Response<List<SleepGoalData>>
}