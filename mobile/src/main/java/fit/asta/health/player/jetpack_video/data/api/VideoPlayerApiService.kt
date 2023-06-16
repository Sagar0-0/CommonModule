package fit.asta.health.player.jetpack_video.data.api

import fit.asta.health.network.data.Status
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaStream
import retrofit2.http.*


//audio - https://asta.fit/tools/media/stream/audio/post/* - multipart Request - json, audio, image, artist
//video - https://asta.fit/tools/media/stream/video/post/* - multipart Request - json, video
//GetAll - https://asta.fit/tools/media/stream/get/all/* - name - (video, audio, instructor)
interface VideoPlayerApiService {

    @GET("media/stream/get/all/")
    suspend fun getAll(
        @Query("name") name: String,
    ): List<MediaStream>

    @POST("media/stream/audio/post/")
    suspend fun postAudio(@Body mediaStream: MediaStream): Status

    @POST("media/stream/video/post/")
    suspend fun postVideo(@Body mediaStream: MediaStream): Status
}
