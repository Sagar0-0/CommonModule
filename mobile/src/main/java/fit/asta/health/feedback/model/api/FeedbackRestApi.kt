package fit.asta.health.feedback.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.feedback.model.network.PostFeedbackRes
import okhttp3.MultipartBody
import okhttp3.OkHttpClient


//Feedback Endpoints
class FeedbackRestApi(baseUrl: String, client: OkHttpClient) : FeedbackApi {

    private val apiService: FeedbackApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(FeedbackApiService::class.java)

    override suspend fun getFeedbackQuestions(userId: String, featureId: String): NetFeedbackRes {
        return apiService.getFeedbackQuestions(userId, featureId)
    }

    override suspend fun postUserFeedback(
        feedback: NetUserFeedback,
        files: List<MultipartBody.Part>
    ): PostFeedbackRes {
        return apiService.postUserFeedback(feedback, files)
    }
}