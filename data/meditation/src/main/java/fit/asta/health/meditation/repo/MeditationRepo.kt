package fit.asta.health.meditation.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.meditation.remote.network.NetMeditationToolResponse
import fit.asta.health.meditation.remote.network.NetMusicRes
import fit.asta.health.meditation.remote.network.NetSheetData
import fit.asta.health.meditation.remote.network.PostRes
import fit.asta.health.meditation.remote.network.PutData
import fit.asta.health.network.data.ServerRes

interface MeditationRepo {
    suspend fun getMeditationTool(
        uid: String,
        date: String
    ): ResponseState<NetMeditationToolResponse>

    suspend fun getMusicTool(uid: String): ResponseState<NetMusicRes>
    suspend fun getSheetData(code: String): ResponseState<List<NetSheetData>>
    suspend fun putMeditationData(putData: PutData): ResponseState<ServerRes>
    suspend fun postMeditationData(postData: PostRes): ResponseState<ServerRes>
}