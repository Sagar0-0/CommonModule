package fit.asta.health.subscription.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData
import fit.asta.health.subscription.remote.model.SubscriptionDurationsData
import fit.asta.health.subscription.remote.model.SubscriptionFinalAmountData
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
        @Query("con") country: String
    ): Response<List<SubscriptionCategoryData>>


    @GET("payment/plan/subscription/duration/get/?")
    suspend fun getSubscriptionDurationsData(
        @Query("con") country: String,
        @Query("id") categoryId: String
    ): Response<SubscriptionDurationsData>

}