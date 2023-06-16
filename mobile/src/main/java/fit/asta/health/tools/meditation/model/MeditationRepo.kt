package fit.asta.health.tools.meditation.model

import fit.asta.health.common.utils.NetworkResult
import fit.asta.health.network.data.Status
import fit.asta.health.tools.meditation.model.network.NetMeditationToolRes
import fit.asta.health.tools.meditation.model.network.NetMusicRes
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.PutData
import kotlinx.coroutines.flow.Flow

interface MeditationRepo {
    fun getMeditationTool(uid:String,date:String): Flow<NetworkResult<NetMeditationToolRes>>
    fun getMusicTool(uid: String): Flow<NetworkResult<NetMusicRes>>
    suspend fun putMeditationData(putData: PutData):Status
    suspend fun postMeditationData(postData: PostRes):Status
}