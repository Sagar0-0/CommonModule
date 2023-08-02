package fit.asta.health.payments.razorpay.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.payments.razorpay.api.PaymentsApi
import fit.asta.health.payments.razorpay.model.OrderRequest
import fit.asta.health.payments.razorpay.model.OrderResponse
import fit.asta.health.payments.razorpay.model.PaymentResponse
import javax.inject.Inject

class PaymentsRepoImpl
@Inject constructor(
    private val remoteApi: PaymentsApi
) : PaymentsRepo {
    override suspend fun createOrder(data: OrderRequest): ResponseState<OrderResponse> {
        return try {
            ResponseState.Success(remoteApi.createOrder(data))
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

    override suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ): ResponseState<PaymentResponse> {
        return try {
            ResponseState.Success(remoteApi.verifyAndUpdateProfile(paymentId, uid))
        } catch (e: Exception) {
            ResponseState.Error(e)
        }
    }

}