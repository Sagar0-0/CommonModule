package fit.asta.health.meditation.remote

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Response
import fit.asta.health.meditation.remote.network.NetMeditationToolResponse
import fit.asta.health.meditation.remote.network.NetMusicRes
import fit.asta.health.meditation.remote.network.PostRes
import fit.asta.health.meditation.remote.network.PutData
import fit.asta.health.network.data.ServerRes
import retrofit2.http.*


interface MeditationApi {
    @GET("tools/meditation/get/?")
    suspend fun getMeditationTool(
        @Query("uid") userId: String,
        @Query("date") date: String
    ): Response<NetMeditationToolResponse>

    @GET("tools/meditation/start/session/get/?")
    suspend fun getMusicTool(
        @Query("uid") userId: String = "6309a9379af54f142c65fbfe"
    ): Response<NetMusicRes>

    @PUT("tools/meditation/put/")
    suspend fun putMeditationData(@Body putData: PutData): Response<ServerRes>

    @POST("tools/meditation/activity/post/")
    suspend fun postMeditationData(@Body postRes: PostRes): Response<ServerRes>

    @GET("tools/health/list/get/?")
    suspend fun getSheetData(
        @Query("screenName") code: String
    ): Response<List<NetSheetData>>

}