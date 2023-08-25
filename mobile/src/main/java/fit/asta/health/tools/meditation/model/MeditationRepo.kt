package fit.asta.health.tools.meditation.model

import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkResult
import fit.asta.health.tools.meditation.model.network.NetMeditationToolRes
import fit.asta.health.tools.meditation.model.network.NetMusicRes
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.PutData
import kotlinx.coroutines.flow.Flow

interface MeditationRepo {
    fun getMeditationTool(uid: String, date: String): Flow<NetworkResult<NetMeditationToolRes>>
    fun getMusicTool(uid: String): Flow<NetworkResult<NetMusicRes>>
    suspend fun putMeditationData(putData: PutData): NetworkResult<ServerRes>
    suspend fun postMeditationData(postData: PostRes): NetworkResult<ServerRes>
}