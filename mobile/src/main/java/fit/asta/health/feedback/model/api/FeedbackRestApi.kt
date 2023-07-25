package fit.asta.health.feedback.model.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.feedback.model.network.UserFeedback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient


//Feedback Endpoints
class FeedbackRestApi(baseUrl: String, client: OkHttpClient) : FeedbackApi {

    private val apiService: FeedbackApiService = NetworkUtil
        .getRetrofit(baseUrl = baseUrl, client = client)
        .create(FeedbackApiService::class.java)

    override suspend fun getFeedbackQuestions(
        userId: String,
        featureId: String
    ): FeedbackQuesResponse {
        return apiService.getFeedbackQuestions(userId, featureId)
    }

    override suspend fun postUserFeedback(
        feedback: UserFeedback,
        files: List<MultipartBody.Part>
    ): PostFeedbackRes {
        return apiService.postUserFeedback(feedback, files)
    }
}