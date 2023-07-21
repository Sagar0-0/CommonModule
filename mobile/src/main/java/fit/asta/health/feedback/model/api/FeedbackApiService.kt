package fit.asta.health.feedback.model.api

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.feedback.model.network.PostFeedbackRes
import fit.asta.health.network.data.Status
import okhttp3.MultipartBody
import retrofit2.http.*

//Feedback Endpoints
interface FeedbackApiService {

    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("fid") featureId: String
    ): NetFeedbackRes

    @Multipart
    @POST("feedback/user/post")
    suspend fun postUserFeedback(
        @Part("json") feedback: NetUserFeedback,
        @Part files: List<MultipartBody.Part>
    ): PostFeedbackRes
}
