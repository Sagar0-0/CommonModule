package fit.asta.health.tools.sleep.model.api

import fit.asta.health.tools.sleep.model.network.common.ToolData
import fit.asta.health.tools.sleep.model.network.disturbance.SleepDisturbanceResponse
import fit.asta.health.tools.sleep.model.network.get.SleepToolGetResponse
import fit.asta.health.tools.sleep.model.network.jetlag.SleepJetLagTipResponse
import fit.asta.health.tools.sleep.model.network.post.SleepPostRequestBody
import fit.asta.health.tools.sleep.model.network.put.SleepPutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface SleepingService {

    @PUT("tools/sleep/put/")
    suspend fun putUserData(
        @Body toolData: ToolData
    ): Response<SleepPutResponse>

    @GET("tools/sleep/get/")
    suspend fun getUserDefaultSettings(
        @Query("uid") userId: String,
        @Query("date") date: String
    ): Response<SleepToolGetResponse>

    @POST("tools/sleep/activity/post/")
    suspend fun postUserReading(
        @Query("uid") userId: String,
        @Body sleepPostRequestBody: SleepPostRequestBody
    ): Response<SleepPutResponse>

    @GET("tools/sleep/property/list/get/")
    suspend fun getPropertyData(
        @Query("uid") userId: String,
        @Query("property") property: String
    ): Response<SleepDisturbanceResponse>

    @GET("tools/help/get/")
    suspend fun getJetLagTips(
        @Query("id") id: String
    ): Response<SleepJetLagTipResponse>
}