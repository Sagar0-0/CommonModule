package fit.asta.health.player.jetpack_video.data.api

import fit.asta.health.network.data.Status
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaStream


interface VideoPlayerApi {

    suspend fun getAll( name:String): List<MediaStream>
    suspend fun postAudio(mediaStream: MediaStream):Status
    suspend fun postVideo(mediaStream: MediaStream):Status
}