package fit.asta.health.tools.breathing.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.breathing.model.network.AllExerciseData
import fit.asta.health.tools.breathing.model.network.CustomRatioData
import fit.asta.health.tools.breathing.model.network.NetGetRes
import fit.asta.health.tools.breathing.model.network.NetGetStart
import fit.asta.health.tools.breathing.model.network.request.CustomRatioPost
import fit.asta.health.tools.breathing.model.network.request.NetPost
import fit.asta.health.tools.breathing.model.network.request.NetPut
import okhttp3.OkHttpClient

class BreathingRestApi(client: OkHttpClient) : BreathingApi {
    private val apiService: BreathingService = NetworkUtil
        .getRetrofit(client)
        .create(BreathingService::class.java)

    override suspend fun getBreathingTool(userId: String, date: String): NetGetRes {
        return apiService.getBreathingTool(userId, date)
    }

    override suspend fun getAllBreathingData(userId: String): AllExerciseData {
        return apiService.getAllBreathingData(userId)
    }

    override suspend fun getStart(userId: String): NetGetStart {
        return apiService.getStart(userId)
    }

    override suspend fun putBreathingData(netPut: NetPut): ServerRes {
        return apiService.putBreathingData(netPut)
    }

    override suspend fun postBreathingData(netPost: NetPost): ServerRes {
        return apiService.postBreathingData(netPost)
    }

    override suspend fun postRatioData(customRatioData: CustomRatioData): ServerRes {
        return apiService.postRatioData(customRatioData)
    }

    override suspend fun deleteRatioData(ratioId: String): ServerRes {
        return apiService.deleteRatioData(ratioId)
    }

    override suspend fun postAdminData(customRatioPost: CustomRatioPost): ServerRes {
        return apiService.postAdminData(customRatioPost)
    }

    override suspend fun deleteAdminData(exerciseId: String): ServerRes {
       return apiService.deleteAdminData(exerciseId)
    }
}