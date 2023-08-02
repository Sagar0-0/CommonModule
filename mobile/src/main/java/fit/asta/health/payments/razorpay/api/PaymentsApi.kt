package fit.asta.health.payments.razorpay.api

import fit.asta.health.payments.razorpay.model.OrderRequest
import fit.asta.health.payments.razorpay.model.OrderResponse
import fit.asta.health.payments.razorpay.model.PaymentResponse

interface PaymentsApi {
    suspend fun createOrder(data: OrderRequest): OrderResponse

    suspend fun verifyAndUpdateProfile(paymentId: String, uid: String): PaymentResponse
}