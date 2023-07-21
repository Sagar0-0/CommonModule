package fit.asta.health.feedback.model

import android.content.Context
import fit.asta.health.common.utils.InputStreamRequestBody
import fit.asta.health.common.utils.getFileName
import fit.asta.health.feedback.model.api.FeedbackApi
import fit.asta.health.feedback.model.domain.Feedback
import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.network.data.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody


class FeedbackRepoImpl(
    private val context: Context,
    private val remoteApi: FeedbackApi,
    private val mapper: FeedbackDataMapper,
) : FeedbackRepo {

    override suspend fun getFeedback(userId: String, featureId: String): Flow<NetFeedbackRes> {
        return flow {
            emit(
                remoteApi.getFeedbackQuestions(userId = userId, featureId = featureId)
            )
        }
    }

    override suspend fun postUserFeedback(feedback: NetUserFeedback): Flow<PostFeedbackRes> {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()

        feedback.ans.forEach {an->
            if (an.media != null) {
                an.media.forEach { media->
                    parts.add(
                        MultipartBody.Part.createFormData(
                            name = "file",
                            filename = media.name,
                            body = InputStreamRequestBody(context.contentResolver, media.localUri)
                        )
                    )
                }
            }
        }

        return flow {
            emit(
                remoteApi.postUserFeedback(feedback,parts)
            )
        }
    }
}