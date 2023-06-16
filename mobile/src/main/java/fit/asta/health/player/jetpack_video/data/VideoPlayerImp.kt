package fit.asta.health.player.jetpack_video.data

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
import fit.asta.health.player.jetpack_video.data.api.VideoPlayerApi
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaData
import fit.asta.health.player.jetpack_video.data.networkmodel.MediaStream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class VideoPlayerImp(
    private val remoteApi :VideoPlayerApi
):VideoPlayerRepo {
    override suspend fun getAll(name: String): Flow<NetworkResult<MediaData>> {
        return flow{
            val result=remoteApi.getAll(name)
            emit(NetworkResult.Loading())
            emit(NetworkResult.Success(data = MediaData(data = result)))
        }.catch {
            emit(NetworkResult.Error(message = it.message))
        }
    }

    override suspend fun postAudio(mediaStream: MediaStream): Status {
        return remoteApi.postAudio(mediaStream)
    }

    override suspend fun postVideo(mediaStream: MediaStream): Status {
       return remoteApi.postVideo(mediaStream)
    }
}