package fit.asta.health.payment.api

import fit.asta.health.payment.model.OrderRequest
import fit.asta.health.payment.model.OrderResponse
import fit.asta.health.payment.model.PaymentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PaymentsApi {
    @POST("payment/create/order/post/")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    @GET("payment/get/?")
    suspend fun verifyAndUpdateProfile(
        @Query("pid") paymentId: String,
        @Query("uid") uid: String
    ): PaymentResponse
}