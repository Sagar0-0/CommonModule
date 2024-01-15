package fit.asta.health.subscription.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.subscription.remote.model.SubscriptionCategoryData
import fit.asta.health.subscription.remote.model.SubscriptionDurationData
import fit.asta.health.subscription.remote.model.SubscriptionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionApi {

    @GET("payment/plan/get/?")
    suspend fun getData(
        @Query("uid") uid: String,
        @Query("con") country: String
    ): Response<SubscriptionResponse>


    @GET("payment/plan/subscription/get/?")
    suspend fun getSubscriptionData(
        @Query("con") country: String
    ): Response<List<SubscriptionCategoryData>>


    @GET("payment/plan/subscription/duration/get/?")
    suspend fun getSubscriptionDurationData(
        @Query("con") country: String,
        @Query("id") id: String
    ): Response<SubscriptionDurationData>

}