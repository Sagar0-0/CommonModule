package fit.asta.health.player.jetpack_video.data

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaData
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaStream
import kotlinx.coroutines.flow.Flow

interface VideoPlayerRepo{
    suspend fun getAll( name:String): Flow<NetworkResult<MediaData>>
    suspend fun postAudio(mediaStream: MediaStream): Status
    suspend fun postVideo(mediaStream: MediaStream): Status
}