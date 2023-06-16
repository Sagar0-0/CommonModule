package fit.asta.health.tools.meditation.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.network.data.Status
import fit.asta.health.tools.meditation.model.network.NetMeditationToolRes
import fit.asta.health.tools.meditation.model.network.NetMusicRes
import fit.asta.health.tools.meditation.model.network.PostRes
import fit.asta.health.tools.meditation.model.network.PutData
import okhttp3.OkHttpClient


class MeditationRestApi(baseUrl: String, client: OkHttpClient) :
    MeditationApi {

    private val apiService: MeditationService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(MeditationService::class.java)

    override suspend fun getMeditationTool(uid: String, date: String): NetMeditationToolRes {
       return apiService.getMeditationTool(userId = uid,date=date)
    }

    override suspend fun getMusicTool(uid: String): NetMusicRes {
       return apiService.getMusicTool(uid)
    }

    override suspend fun putMeditationData(putData: PutData): Status {
        return apiService.putMeditationData(putData)
    }

    override suspend fun postMeditationData(postRes: PostRes): Status {
       return apiService.postMeditationData(postRes)
    }


}