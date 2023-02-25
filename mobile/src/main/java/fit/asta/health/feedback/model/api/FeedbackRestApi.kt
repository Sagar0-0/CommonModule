package fit.asta.health.feedback.model.api

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.network.data.Status
import fit.asta.health.utils.NetworkUtil
import okhttp3.OkHttpClient


//Feedback Endpoints
class FeedbackRestApi(baseUrl: String, client: OkHttpClient) : FeedbackApi {

    private val apiService: FeedbackApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(FeedbackApiService::class.java)

    override suspend fun getFeedbackQuestions(userId: String, featureId: String): NetFeedbackRes {
        return apiService.getFeedbackQuestions(userId, featureId)
    }

    override suspend fun postUserFeedback(feedback: NetUserFeedback): Status {
        return apiService.postUserFeedback(feedback)
    }
}