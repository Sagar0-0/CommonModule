package fit.asta.health.feedback.viewmodel

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.PostFeedbackRes


sealed class FeedbackQuesState {
    object Loading : FeedbackQuesState()
    class Success(val feedback: NetFeedbackRes) : FeedbackQuesState()
    class Error(val error: Throwable) : FeedbackQuesState()
}

sealed class FeedbackPostState {

    object Idle : FeedbackPostState()
    object Loading : FeedbackPostState()
    class Success(val postFeedbackRes: PostFeedbackRes) : FeedbackPostState()
    class Error(val error: Throwable) : FeedbackPostState()
}

