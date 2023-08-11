package fit.asta.health.feedback.data.remote.api

import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
import okhttp3.MultipartBody

//Feedback Endpoints
interface FeedbackApi {

    suspend fun getFeedbackQuestions(userId: String, featureId: String): FeedbackQuesDTO
    suspend fun postUserFeedback(
        feedback: UserFeedbackDTO,
        files: List<MultipartBody.Part>
    ): PostFeedbackDTO
}