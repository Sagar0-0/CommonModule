package fit.asta.health.feedback.model.api

import fit.asta.health.feedback.model.network.NetFeedbackRes
import fit.asta.health.feedback.model.network.NetUserFeedback
import fit.asta.health.network.data.Status
import retrofit2.http.*

//Feedback Endpoints
interface FeedbackApiService {

    @GET("feedback/user/get/?")
    suspend fun getFeedbackQuestions(
        @Query("uid") userId: String,
        @Query("fid") featureId: String
    ): NetFeedbackRes

    @POST("feedback/user/post")
    suspend fun postUserFeedback(@Body feedback: NetUserFeedback): Status
}
