package fit.asta.health.feedback.data.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.InputStreamRequestBody
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.toResponseState
import fit.asta.health.feedback.data.remote.api.FeedbackApi
import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
import okhttp3.MultipartBody


class FeedbackRepoImpl(
    private val remoteApi: FeedbackApi,
    private val contentResolver: ContentResolver
) : FeedbackRepo {

    override suspend fun getFeedbackQuestions(
        userId: String,
        featureId: String
    ): ResponseState<FeedbackQuesDTO> {
        return remoteApi.getFeedbackQuestions(userId = userId, featureId = featureId).toResponseState()
    }

    override suspend fun postUserFeedback(feedback: UserFeedbackDTO): ResponseState<PostFeedbackDTO> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()

        feedback.ans.forEach { an ->
            if (an.media != null) {
                an.media.forEach { media ->
                    parts.add(
                        MultipartBody.Part.createFormData(
                            name = "file",
                            filename = media.name,
                            body = InputStreamRequestBody(contentResolver, media.localUri)
                        )
                    )
                }
            }
        }

        return remoteApi.postUserFeedback(feedback,parts).toResponseState()
    }
}