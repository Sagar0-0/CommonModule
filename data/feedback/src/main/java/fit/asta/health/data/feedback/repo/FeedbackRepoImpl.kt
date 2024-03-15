package fit.asta.health.data.feedback.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.feedback.remote.FeedbackApi
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
import fit.asta.health.data.feedback.remote.modal.PostFeedback
import fit.asta.health.data.feedback.remote.modal.UserFeedback
import fit.asta.health.network.utils.InputStreamRequestBody
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import javax.inject.Inject

class FeedbackRepoImpl
@Inject constructor(
    private val remoteApi: FeedbackApi,
    private val contentResolver: ContentResolver,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : FeedbackRepo {

    override suspend fun getFeedbackQuestions(
        userId: String,
        feature: String
    ): ResponseState<FeedbackQuestions> = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getFeedbackQuestions(userId = userId, feature = feature)
        }
    }

    override suspend fun postUserFeedback(feedback: UserFeedback): ResponseState<PostFeedback> {
        val parts: ArrayList<MultipartBody.Part> = getMultipartBodyParts(feedback)
        return withContext(coroutineDispatcher) {
            getApiResponseState { remoteApi.postUserFeedback(feedback, parts) }
        }
    }

    private fun getMultipartBodyParts(feedback: UserFeedback): ArrayList<MultipartBody.Part> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()

        feedback.answers.forEach { an ->
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
        return parts
    }
}