package fit.asta.health.payments.razorpay.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.razorpay.model.OrderRequest
import fit.asta.health.payments.razorpay.model.OrderResponse
import fit.asta.health.payments.razorpay.model.PaymentResponse

interface PaymentsRepo {

    suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse>

    suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<PaymentResponse>
}