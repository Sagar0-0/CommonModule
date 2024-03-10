package fit.asta.health.data.breathing.model.api

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Response
import fit.asta.health.data.breathing.model.network.AllExerciseData
import fit.asta.health.data.breathing.model.network.CustomRatioData
import fit.asta.health.data.breathing.model.network.NetGetRes
import fit.asta.health.data.breathing.model.network.NetGetStart
import fit.asta.health.data.breathing.model.network.request.NetPost
import fit.asta.health.data.breathing.model.network.request.NetPut
import fit.asta.health.network.data.ServerRes
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface BreathingApi {

    //https://asta.fit/tools/breathing/put/
    //https://asta.fit/tools/breathing/get/?uid=uId&date=2023-03-09
    //https://asta.fit/tools/breathing/activity/post/
    //https://asta.fit/tools/breathing/start/session/get/?uid=
    //
    //https://asta.fit/tools/breathing/exercise/get/all/?uid=
    //
    //https://asta.fit/tools/breathing/ratio/post/
    //https://asta.fit/tools/breathing/ratio/delete/?rid=
    //
    //admin:
    //https://asta.fit/tools/breathing/exercise/post/
    //https://asta.fit/tools/breathing/exercise/delete/?eid=

    @GET("tools/breathing/get/?")
    suspend fun getBreathingTool(
        @Query("uid") userId: String,
        @Query("date") date: Long
    ): Response<NetGetRes>

    @GET("tools/breathing/exercise/get/all/?")
    suspend fun getAllBreathingData(
        @Query("uid") userId: String
    ): Response<AllExerciseData>

    @GET("tools/breathing/start/session/get/?")
    suspend fun getStart(
        @Query("uid") userId: String
    ): Response<NetGetStart>

    @PUT("tools/breathing/put/")
    suspend fun putBreathingData(
        @Body netPut: NetPut
    ): Response<ServerRes>

    @POST("tools/breathing/activity/post/?")
    suspend fun postBreathingData(
        @Body netPost: NetPost
    ): Response<ServerRes>

    @POST("tools/breathing/ratio/post/?")
    suspend fun postRatioData(
        @Body customRatioData: CustomRatioData
    ): Response<ServerRes>

    @DELETE("tools/breathing/ratio/delete/?")
    suspend fun deleteRatioData(
        @Query("rid") ratioId: String
    ): Response<ServerRes>

    @GET("tools/health/list/get/?")
    suspend fun getSheetData(
        @Query("screenName") code: String
    ): Response<List<NetSheetData>>

//    @POST("tools/breathing/exercise/post/?")
//    suspend fun postAdminData(
//        @Body customRatioPost: CustomRatioPost
//    ): Response<ServerRes>
//
//    @DELETE("tools/breathing/exercise/delete/?")
//    suspend fun deleteAdminData(
//        @Query("eid") exerciseId: String
//    ): Response<ServerRes>
}