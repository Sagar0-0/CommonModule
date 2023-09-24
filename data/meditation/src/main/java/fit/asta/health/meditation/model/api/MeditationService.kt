package fit.asta.health.meditation.model.api

import fit.asta.health.meditation.model.network.*
import fit.asta.health.network.data.ServerRes
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
    suspend fun putMeditationData( @Body putData: PutData): ServerRes

    @POST("tools/meditation/activity/post/")
    suspend fun postMeditationData( @Body postRes: PostRes): ServerRes


}