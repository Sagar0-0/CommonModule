package fit.asta.health.feedback.intent

import fit.asta.health.feedback.model.domain.Feedback


sealed class FeedbackState {
    object Loading : FeedbackState()
    class Success(val feedback: Feedback) : FeedbackState()
    class Error(val error: Throwable) : FeedbackState()
}