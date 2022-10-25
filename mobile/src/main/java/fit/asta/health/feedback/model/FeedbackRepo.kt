package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow


interface FeedbackRepo {
    suspend fun getFeedback(userId: String, featureId: String): Flow<Feedback>
    suspend fun postUserFeedback(feedback: NetUserFeedback): Flow<Status>
}