package fit.asta.health.payments.sub.api

import fit.asta.health.payments.sub.model.SubscriptionResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SubscriptionApiService {

    @GET("payment/plan/get/?")
    suspend fun getData(
        @Query("uid") uid: String,
        @Query("con") country: String,
        @Query("date") date: String,
    ): SubscriptionResponse

}