package fit.asta.health.player.jetpack_video.data.api

import fit.asta.health.network.data.Status
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaStream
import okhttp3.OkHttpClient


//Video Endpoints
class VideoPlayerApiImp(baseUrl: String, client: OkHttpClient) :
    VideoPlayerApi {

    private val apiService: VideoPlayerApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(VideoPlayerApiService::class.java)

    override suspend fun getAll(name: String): List<MediaStream> {
       return apiService.getAll(name)
    }

    override suspend fun postAudio(mediaStream: MediaStream): Status {
        return apiService.postAudio(mediaStream)
    }

    override suspend fun postVideo(mediaStream: MediaStream): Status {
        return apiService.postVideo(mediaStream)
    }


}