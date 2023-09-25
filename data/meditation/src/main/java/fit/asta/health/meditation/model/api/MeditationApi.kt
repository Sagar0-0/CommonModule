package fit.asta.health.meditation.model.api

import fit.asta.health.meditation.model.network.NetMeditationToolRes
import fit.asta.health.meditation.model.network.NetMusicRes
import fit.asta.health.meditation.model.network.PostRes
import fit.asta.health.meditation.model.network.PutData
import fit.asta.health.network.data.ServerRes

interface MeditationApi {
    suspend fun getMeditationTool(uid: String, date: String): NetMeditationToolRes

    suspend fun getMusicTool(uid: String): NetMusicRes

    suspend fun putMeditationData(putData: PutData): ServerRes

    suspend fun postMeditationData(postRes: PostRes): ServerRes
}