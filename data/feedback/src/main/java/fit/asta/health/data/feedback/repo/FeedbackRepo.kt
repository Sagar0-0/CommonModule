package fit.asta.health.data.feedback.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
import fit.asta.health.data.feedback.remote.modal.PostFeedback
import fit.asta.health.data.feedback.remote.modal.UserFeedback

interface FeedbackRepo {
    suspend fun getFeedbackQuestions(
        userId: String,
        feature: String
    ): ResponseState<FeedbackQuestions>

    suspend fun postUserFeedback(feedback: UserFeedback): ResponseState<PostFeedback>
}