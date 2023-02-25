package fit.asta.health.feedback.model.api

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.network.data.Status

//Feedback Endpoints
interface FeedbackApi {

    suspend fun getFeedbackQuestions(userId: String, featureId: String): NetFeedbackRes
    suspend fun postUserFeedback(feedback: NetUserFeedback): Status
}