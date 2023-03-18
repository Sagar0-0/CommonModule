package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.request.PostReq
import fit.asta.health.tools.walking.model.network.request.PutReq
import fit.asta.health.tools.walking.model.network.response.*
import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient
import java.util.*


//Health Tool - Walking Endpoints
class WalkingRestApi(baseUrl: String, client: OkHttpClient) :
    WalkingApi {

    private val apiService: WalkingService = NetworkUtil
        .getRetrofit(baseUrl, client)
        .create(WalkingService::class.java)



    override suspend fun getStepsDataWithUidDate(userId: String, date: Double): StepsDataWithUidDate {
        return apiService.getDataWithUidDate(userId,date)
    }

    override suspend fun getStepsDataWithUid(userId: String): StepsDataWithUid {
        return apiService.getDataWithUid(userId)
    }

    override suspend fun putData(putReq: PutReq): PutResponse {
        return apiService.putData(putReq)
    }

    override suspend fun postData(postReq: PostReq): PostResponse {
        return apiService.postData(postReq)
    }
}