package fit.asta.health.tools.exercise.model.api

import fit.asta.health.network.data.ServerRes
import fit.asta.health.network.utils.NetworkUtil
import fit.asta.health.tools.exercise.model.network.NetGetRes
import fit.asta.health.tools.exercise.model.network.NetGetStart
import fit.asta.health.tools.exercise.model.network.NetPost
import fit.asta.health.tools.exercise.model.network.NetPutRes
import okhttp3.OkHttpClient

class ExerciseRestApi(baseUrl: String, client: OkHttpClient) :ExerciseApi{
    private val apiService: ExerciseService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(ExerciseService::class.java)

    override suspend fun getExerciseTool(uid: String, date: String, name: String): NetGetRes {
        return apiService.getExerciseTool(userId = uid,date, name)
    }

    override suspend fun getStart(uid: String,name: String): NetGetStart {
        return apiService.getStart(userId = uid, name = name)
    }

    override suspend fun putExerciseData(netPutRes: NetPutRes, name: String): ServerRes {
      return apiService.putExerciseData(name, netPutRes)
    }

    override suspend fun postExerciseData(netPost: NetPost, name: String): ServerRes {
        return apiService.postExerciseData(name, netPost)
    }

}