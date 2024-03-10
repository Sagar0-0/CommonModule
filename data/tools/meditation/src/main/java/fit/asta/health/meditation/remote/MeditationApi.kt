package fit.asta.health.meditation.remote

import fit.asta.health.common.utils.NetSheetData
import fit.asta.health.common.utils.Response
import fit.asta.health.meditation.remote.network.NetMeditationToolResponse
import fit.asta.health.meditation.remote.network.NetMusicRes
import fit.asta.health.meditation.remote.network.PostRes
import fit.asta.health.meditation.remote.network.PutData
import fit.asta.health.network.data.ServerRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface MeditationApi {
    @GET("tools/meditation/get/?")
    suspend fun getMeditationTool(
        @Query("uid") userId: String,
        @Query("date") date: Long
    ): Response<NetMeditationToolResponse>

    @GET("tools/meditation/start/session/get/?")
    suspend fun getMusicTool(
        @Query("uid") userId: String
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