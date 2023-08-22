package fit.asta.health.payment.repo

import fit.asta.health.payment.api.PaymentsApi
import fit.asta.health.payment.model.OrderRequest
import fit.asta.health.common.utils.getResponseState
import javax.inject.Inject

class PaymentsRepoImpl
@Inject constructor(
    private val remoteApi: PaymentsApi
) : PaymentsRepo {
    override suspend fun createOrder(data: OrderRequest) = getResponseState {
        remoteApi.createOrder(data)
    }

    override suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ) = getResponseState { remoteApi.verifyAndUpdateProfile(paymentId, uid) }

}