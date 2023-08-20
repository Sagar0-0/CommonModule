package com.example.payment.repo

import com.example.common.utils.ResponseState
import com.example.payment.model.OrderRequest
import com.example.payment.model.OrderResponse
import com.example.payment.model.PaymentResponse

interface PaymentsRepo {

    suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse>

    suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<PaymentResponse>
}