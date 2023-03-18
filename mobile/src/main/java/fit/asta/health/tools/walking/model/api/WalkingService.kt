package fit.asta.health.tools.walking.model.api

import fit.asta.health.tools.walking.model.network.request.*
import fit.asta.health.tools.walking.model.network.response.*
import retrofit2.http.*
import java.util.Date


//Health Tool - Walking Endpoints
interface WalkingService {



    @GET("tools/steps/get")
    suspend fun getDataWithUidDate(
        @Query("uid") userId: String,
        @Query("date") date:Double
    ): StepsDataWithUidDate

    @GET("tools/steps/start/session/get")
    suspend fun getDataWithUid(
        @Query("uid") userId: String
    ): StepsDataWithUid

    @PUT("tools/steps/put")
    suspend fun putData(
        @Body putReq: PutReq
    ) :PutResponse

    @POST("tools/steps/activity/post")
    suspend fun postData(
        @Body postReq: PostReq
    ):PostResponse


}
