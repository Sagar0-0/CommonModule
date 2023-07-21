package fit.asta.health.tools.breathing.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.CustomRatioData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface BreathingService {

    //https://asta.fit/tools/breathing/put/
    //https://asta.fit/tools/breathing/get/?uid=6309a9379af54f142c65fbfe&date=2023-03-09
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
        @Query("date") date: String
    ): NetGetRes

    @GET("tools/breathing/exercise/get/all/?")
    suspend fun getAllBreathingData(
        @Query("uid") userId: String
    ): AllExerciseData

    @GET("tools/breathing/start/session/get/?")
    suspend fun getStart(
        @Query("uid") userId: String = "6309a9379af54f142c65fbfe"
    ): NetGetStart

    @PUT("tools/breathing/put/")
    suspend fun putBreathingData(
        @Body netPut: NetPut
    ): ServerRes

    @POST("tools/breathing/activity/post/?")
    suspend fun postBreathingData(
        @Body netPost: NetPost
    ): ServerRes

    @POST("tools/breathing/ratio/post/?")
    suspend fun postRatioData(
        @Body customRatioData: CustomRatioData
    ): ServerRes

    @DELETE("tools/breathing/ratio/delete/?")
    suspend fun deleteRatioData(
        @Query("rid") ratioId: String
    ): ServerRes

    @POST("tools/breathing/exercise/post/?")
    suspend fun postAdminData(
        @Body customRatioPost: CustomRatioPost
    ): ServerRes

    @DELETE("tools/breathing/exercise/delete/?")
    suspend fun deleteAdminData(
        @Query("eid") exerciseId: String
    ): ServerRes
}