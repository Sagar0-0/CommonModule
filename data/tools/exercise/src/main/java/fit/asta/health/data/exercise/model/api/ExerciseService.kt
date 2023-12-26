package fit.asta.health.data.exercise.model.api

import fit.asta.health.data.exercise.model.network.NetGetRes
import fit.asta.health.data.exercise.model.network.NetGetStart
import fit.asta.health.data.exercise.model.network.NetPost
import fit.asta.health.data.exercise.model.network.NetPutRes
import fit.asta.health.network.data.ServerRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ExerciseService {


    @GET("tools/exercise/get/?")
    suspend fun getExerciseTool(
        @Query("uid") userId: String,
        @Query("date") date: String,
        @Query("exName") name: String
    ): NetGetRes

    @GET("tools/exercise/start/get/?")
    suspend fun getStart(
        @Query("uid") userId: String = "6309a9379af54f142c65fbfe",
        @Query("exName") name: String,
    ): NetGetStart

    @PUT("tools/exercise/put/?")
    suspend fun putExerciseData(
        @Query("exName") name: String,
        @Body netPutRes: NetPutRes
    ): ServerRes

    @POST("tools/exercise/activity/post/?")
    suspend fun postExerciseData(
        @Query("exName") name: String,
        @Body netPost: NetPost
    ): ServerRes
}