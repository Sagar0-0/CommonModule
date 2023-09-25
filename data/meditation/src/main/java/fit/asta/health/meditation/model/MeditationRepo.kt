package fit.asta.health.meditation.model

import fit.asta.health.meditation.model.network.NetMeditationToolRes
import fit.asta.health.meditation.model.network.NetMusicRes
import fit.asta.health.meditation.model.network.PostRes
import fit.asta.health.meditation.model.network.PutData
import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkResult
import kotlinx.coroutines.flow.Flow

interface MeditationRepo {
    fun getMeditationTool(uid: String, date: String): Flow<NetworkResult<NetMeditationToolRes>>
    fun getMusicTool(uid: String): Flow<NetworkResult<NetMusicRes>>
    suspend fun putMeditationData(putData: PutData): NetworkResult<ServerRes>
    suspend fun postMeditationData(postData: PostRes): NetworkResult<ServerRes>
}