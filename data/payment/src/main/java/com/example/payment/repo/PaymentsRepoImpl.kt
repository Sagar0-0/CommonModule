package com.example.payment.repo

import com.example.common.utils.ResponseState
import com.example.common.utils.getResponseState
import com.example.payment.api.PaymentsApi
import com.example.payment.model.OrderRequest
import com.example.payment.model.OrderResponse
import com.example.payment.model.PaymentResponse
import javax.inject.Inject

class PaymentsRepoImpl
@Inject constructor(
    private val remoteApi: PaymentsApi
) : PaymentsRepo {
    override suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse> {
        return getResponseState {
            remoteApi.createOrder(data)
        }

    }

    override suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<PaymentResponse> {
        return getResponseState { remoteApi.verifyAndUpdateProfile(paymentId, uid) }
    }

}