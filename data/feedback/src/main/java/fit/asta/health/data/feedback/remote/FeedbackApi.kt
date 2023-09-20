package fit.asta.health.data.feedback.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.feedback.remote.modal.FeedbackQuesDTO
import fit.asta.health.data.feedback.remote.modal.PostFeedbackDTO
import fit.asta.health.data.feedback.remote.modal.UserFeedbackDTO
import okhttp3.MultipartBody
import retrofit2.http.*

//Feedback Endpoints
interface FeedbackApi {

    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("feature") feature: String
    ): Response<FeedbackQuesDTO>

    @Multipart
    @POST("feedback/user/post")
    suspend fun postUserFeedback(
        @Part("json") feedback: UserFeedbackDTO,
        @Part files: List<MultipartBody.Part>
    ): Response<PostFeedbackDTO>
}
