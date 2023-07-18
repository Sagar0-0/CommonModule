package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.feedback.model.network.PostFeedbackRes
import kotlinx.coroutines.flow.Flow

interface FeedbackRepo {
    suspend fun getFeedback(userId: String, featureId: String): Flow<NetFeedbackRes>
    suspend fun postUserFeedback(feedback: NetUserFeedback): Flow<PostFeedbackRes>
}