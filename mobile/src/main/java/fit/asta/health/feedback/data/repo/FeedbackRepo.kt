package fit.asta.health.feedback.data.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO

interface FeedbackRepo {
    suspend fun getFeedback(userId: String, featureId: String): ResponseState<FeedbackQuesDTO>
    suspend fun postUserFeedback(feedback: UserFeedbackDTO): ResponseState<PostFeedbackDTO>
}