package fit.asta.health.data.exercise.model.api

import fit.asta.health.common.utils.Response
import fit.asta.health.data.exercise.model.network.ExerciseData
import fit.asta.health.data.exercise.model.network.NetGetStart
import fit.asta.health.data.exercise.model.network.NetPost
import fit.asta.health.data.exercise.model.network.NetPutRes
import fit.asta.health.network.data.ServerRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ExerciseApi {

    @GET("tools/exercise/get/?")
    suspend fun getExerciseTool(
        @Query("uid") userId: String,
        @Query("date") date: String,
        @Query("exName") name: String
    ): Response<ExerciseData>

    @GET("tools/exercise/start/get/?")
    suspend fun getStart(
        @Query("uid") userId: String,
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