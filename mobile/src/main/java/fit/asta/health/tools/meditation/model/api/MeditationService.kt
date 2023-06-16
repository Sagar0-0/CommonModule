package fit.asta.health.tools.meditation.model.api

import fit.asta.health.network.data.Status
import fit.asta.health.tools.meditation.model.network.*
import retrofit2.http.*


interface MeditationService {

    @GET("tools/meditation/get/?")
    suspend fun getMeditationTool(
        @Query("uid") userId: String,
        @Query("date") date: String
    ): NetMeditationToolRes

    @GET("tools/meditation/start/session/get/?")
    suspend fun getMusicTool(
        @Query("uid") userId: String="6309a9379af54f142c65fbfe"
    ): NetMusicRes

    @PUT("tools/meditation/put/")
    suspend fun putMeditationData( @Body putData: PutData): Status

    @POST("tools/meditation/activity/post/")
    suspend fun postMeditationData( @Body postRes: PostRes): Status


}