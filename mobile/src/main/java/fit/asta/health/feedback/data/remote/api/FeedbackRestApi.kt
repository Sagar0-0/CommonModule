package fit.asta.health.feedback.data.remote.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
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
    ): FeedbackQuesDTO {
        return apiService.getFeedbackQuestions(userId, featureId)
    }

    override suspend fun postUserFeedback(
        feedback: UserFeedbackDTO,
        files: List<MultipartBody.Part>
    ): PostFeedbackDTO {
        return apiService.postUserFeedback(feedback, files)
    }
}