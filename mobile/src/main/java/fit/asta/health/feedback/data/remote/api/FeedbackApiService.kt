package fit.asta.health.feedback.data.remote.api

import fit.asta.health.feedback.data.remote.modal.FeedbackQuesDTO
import fit.asta.health.feedback.data.remote.modal.PostFeedbackDTO
import fit.asta.health.feedback.data.remote.modal.UserFeedbackDTO
import okhttp3.MultipartBody
import retrofit2.http.*

//Feedback Endpoints
interface FeedbackApiService {

    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("fid") featureId: String
    ): FeedbackQuesDTO

    @Multipart
    @POST("feedback/user/post")
    suspend fun postUserFeedback(
        @Part("json") feedback: UserFeedbackDTO,
        @Part files: List<MultipartBody.Part>
    ): PostFeedbackDTO
}
