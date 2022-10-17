package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.domain.Feedback
import kotlinx.coroutines.flow.Flow


interface FeedbackRepo {
    suspend fun getFeedback(userId: String): Flow<Feedback>
}