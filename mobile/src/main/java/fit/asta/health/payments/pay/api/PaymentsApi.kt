package fit.asta.health.payments.pay.api

import fit.asta.health.payments.pay.model.OrderRequest
import fit.asta.health.payments.pay.model.OrderResponse
import fit.asta.health.payments.pay.model.PaymentResponse

interface PaymentsApi {
    suspend fun createOrder(data: OrderRequest): OrderResponse

    suspend fun verifyAndUpdateProfile(paymentId: String, uid: String): PaymentResponse
}