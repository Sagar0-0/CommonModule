package fit.asta.health.subscription.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData
import fit.asta.health.subscription.remote.model.SubscriptionFinalAmountData
import fit.asta.health.subscription.remote.model.SubscriptionPlansResponse
import fit.asta.health.subscription.remote.model.UserSubscribedPlan
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionApi {

    @GET("payment/plan/details/get/?")
    suspend fun getFinalAmountData(
        @Query("type") type: String,
        @Query("catId") categoryId: String,
        @Query("prodId") productId: String,
        @Query("con") country: String
    ): Response<SubscriptionFinalAmountData>

    @GET("payment/plan/subscription/get/?")
    suspend fun getSubscriptionData(
        @Query("con") country: String,
        @Query("uid") uid: String
    ): Response<SubscriptionPlansResponse>

    @GET("payment/plan/get/?")
    suspend fun getUserSubscribedPlan(
        @Query("uid") uid: String
    ): Response<UserSubscribedPlan>


    @GET("payment/plan/subscription/duration/get/?")
    suspend fun getSubscriptionDurationsData(
        @Query("con") country: String,
        @Query("id") categoryId: String
    ): Response<SubscriptionDurationsData>

}