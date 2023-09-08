package fit.asta.health.data.feedback.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.PostFeedbackDTO
import fit.asta.health.data.feedback.remote.modal.UserFeedbackDTO

interface FeedbackRepo {
    suspend fun getFeedbackQuestions(
        userId: String,
        feature: String
    ): ResponseState<FeedbackQuesDTO>

    suspend fun postUserFeedback(feedback: UserFeedbackDTO): ResponseState<PostFeedbackDTO>
}