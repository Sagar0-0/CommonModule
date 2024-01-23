package fit.asta.health.payment.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse
import fit.asta.health.payment.remote.model.PaymentCancelResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PaymentsApi {
    @POST("payment/create/order/post/")
    suspend fun createOrder(@Body orderRequest: OrderRequest): Response<OrderResponse>


    @PUT("payment/cancel/put/?")
    suspend fun informCancelledPayment(
        @Query("oid") orderId: String,
        @Query("uid") uid: String
    ): Response<PaymentCancelResponse>

    @GET("payment/get/?")
    suspend fun verifyAndUpdateProfile(
        @Query("pid") paymentId: String,
        @Query("uid") uid: String
    ): Response<String>
}