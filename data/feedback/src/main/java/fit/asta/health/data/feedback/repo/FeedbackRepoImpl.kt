package fit.asta.health.data.feedback.repo

import android.content.ContentResolver
import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.feedback.remote.FeedbackApi
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.PostFeedbackDTO
import fit.asta.health.data.feedback.remote.modal.UserFeedbackDTO
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
    ): ResponseState<FeedbackQuesDTO> = withContext(coroutineDispatcher) {
        getApiResponseState {
            remoteApi.getFeedbackQuestions(userId = userId, feature = feature)
        }
    }

    override suspend fun postUserFeedback(feedback: UserFeedbackDTO): ResponseState<PostFeedbackDTO> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()

        feedback.ans.forEach { an ->
            an.media?.forEach { media ->
                parts.add(
                    MultipartBody.Part.createFormData(
                        name = "file",
                        filename = media.name,
                        body = InputStreamRequestBody(contentResolver, media.localUri)
                    )
                )
            }
        }
        return withContext(coroutineDispatcher) {
            getApiResponseState { remoteApi.postUserFeedback(feedback, parts) }
        }
    }
}