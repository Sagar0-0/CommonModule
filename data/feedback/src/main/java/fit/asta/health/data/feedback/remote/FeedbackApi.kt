package fit.asta.health.data.feedback.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.feedback.remote.modal.FeedbackQuestions
import fit.asta.health.data.feedback.remote.modal.PostFeedback
import fit.asta.health.data.feedback.remote.modal.UserFeedback
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

//Feedback Endpoints
interface FeedbackApi {

    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("feature") feature: String
    ): Response<FeedbackQuestions>

    @Multipart
    @POST("feedback/user/post")
    suspend fun postUserFeedback(
        @Part("json") feedback: UserFeedback,
        @Part files: List<MultipartBody.Part>
    ): Response<PostFeedback>
}
