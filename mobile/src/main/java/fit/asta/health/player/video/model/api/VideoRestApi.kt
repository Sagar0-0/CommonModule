package fit.asta.health.player.video.model.api

import fit.asta.health.player.video.model.networkdata.SessionResponse
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient


//Video Endpoints
class VideoRestApi(baseUrl: String, client: OkHttpClient) :
    VideoApi {

    private val apiService: VideoApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(VideoApiService::class.java)

    override suspend fun getSession(userId: String, courseId: String, sessionId: String): SessionResponse {
        return apiService.getSession(userId, courseId, sessionId)
    }
}