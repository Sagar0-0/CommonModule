package fit.asta.health.feedback.model

import fit.asta.health.feedback.model.network.FeedbackQuesResponse
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.feedback.model.network.UserFeedback
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFeedbackRepoImpl : FeedbackRepo {

    private var uId = ""

    fun setUid(id: String) {
        uId = id
    }

    override suspend fun getFeedback(
        userId: String,
        featureId: String
    ): Flow<FeedbackQuesResponse> {
        return if (uId == "valid" && featureId == "valid") {
            flow { emit(FeedbackQuesResponse()) }
        } else {
            flow { throw Exception("Error") }
        }
    }

    override suspend fun postUserFeedback(feedback: UserFeedback): Flow<PostFeedbackRes> {
        return if (feedback.ans.isEmpty()) {
            flow { throw Exception() }
        } else {
            flow { emit(PostFeedbackRes()) }
        }
    }
}