package fit.asta.health.feedback.model.api

import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.feedback.model.network.UserFeedback
import okhttp3.MultipartBody

//Feedback Endpoints
interface FeedbackApi {

    suspend fun getFeedbackQuestions(userId: String, featureId: String): FeedbackQuesResponse
    suspend fun postUserFeedback(
        feedback: UserFeedback,
        files: List<MultipartBody.Part>
    ): PostFeedbackRes
}