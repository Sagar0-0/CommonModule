package com.example.payment.api

import com.example.payment.model.OrderRequest
import com.example.payment.model.OrderResponse
import com.example.payment.model.PaymentResponse
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