package fit.asta.health.player.video.model.api

import fit.asta.health.player.video.model.networkdata.SessionResponse
import retrofit2.http.*

//Video Endpoints
interface VideoApiService {

    @GET("course/session/get")
    suspend fun getSession(
        @Query("userId") userId: String,
        @Query("courseId") courseId: String,
        @Query("sessionId") sessionId: String
    ): SessionResponse
}
