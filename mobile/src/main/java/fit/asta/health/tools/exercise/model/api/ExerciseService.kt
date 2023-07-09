package fit.asta.health.tools.exercise.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.tools.exercise.model.network.NetGetRes
import fit.asta.health.tools.exercise.model.network.NetGetStart
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface ExerciseService {
    //https://asta.fit/tools/exercise/put/?exName=yoga
    //https://asta.fit/tools/exercise/get/?uid=6309a9379af54f142c65fbfe&date=2023-03-09&exName=yoga
    //https://asta.fit/tools/exercise/activity/post/?exName=yoga
    //https://asta.fit/tools/exercise/start/get/?uid=6309a9379af54f142c65fbfe&exName=yoga
   // query Param(exName): yoga, dance, hiit, workout

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