package fit.asta.health.subscription.remote

import fit.asta.health.subscription.remote.model.SubscriptionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionApi {

    @GET("payment/plan/get/?")
    suspend fun getData(
        @Query("uid") uid: String,
        @Query("con") country: String
    ): SubscriptionResponse

}