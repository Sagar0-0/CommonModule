package fit.asta.health.payment.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.OrderResponse

interface PaymentsRepo {

    suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse>

    suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<Unit>
}