package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.feedback.model.network.UserFeedback
import kotlinx.coroutines.flow.Flow

interface FeedbackRepo {
    suspend fun getFeedback(userId: String, featureId: String): Flow<FeedbackQuesResponse>
    suspend fun postUserFeedback(feedback: UserFeedback): Flow<PostFeedbackRes>
}