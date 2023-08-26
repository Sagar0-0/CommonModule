package fit.asta.health.payment.repo

import fit.asta.health.common.utils.getResponseState
import fit.asta.health.payment.remote.PaymentsApi
import fit.asta.health.payment.remote.model.OrderRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentsRepoImpl
@Inject constructor(
    private val remoteApi: PaymentsApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PaymentsRepo {
    override suspend fun createOrder(data: OrderRequest) = withContext(coroutineDispatcher) {
        getResponseState {
            remoteApi.createOrder(data)
        }
    }

    override suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ) = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.verifyAndUpdateProfile(paymentId, uid) }
    }

}