package fit.asta.health.payment.repo

import fit.asta.health.payment.model.OrderRequest
import fit.asta.health.payment.model.OrderResponse
import fit.asta.health.payment.model.PaymentResponse
import fit.asta.health.common.utils.ResponseState

interface PaymentsRepo {

    suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse>

    suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<PaymentResponse>
}