package fit.asta.health.payment.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.payment.remote.PaymentsApi
import fit.asta.health.payment.remote.model.OrderRequest
import fit.asta.health.payment.remote.model.PaymentCancelResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PaymentsRepoImpl
@Inject constructor(
    private val remoteApi: PaymentsApi,
    private val apiErrorHandler: PaymentsApiErrorHandler = PaymentsApiErrorHandler(),
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PaymentsRepo {


    override suspend fun createOrder(data: OrderRequest) = withContext(coroutineDispatcher) {
        getApiResponseState(errorHandler = apiErrorHandler) {
            remoteApi.createOrder(data)
        }
    }

    override suspend fun verifyAndUpdateProfile(
        paymentId: String,
        uid: String
    ) = withContext(coroutineDispatcher) {
        getApiResponseState(errorHandler = apiErrorHandler) {
            remoteApi.verifyAndUpdateProfile(
                paymentId,
                uid
            )
        }
    }

    override suspend fun informCancelledPayment(
        orderId: String,
        uid: String
    ): ResponseState<PaymentCancelResponse> = withContext(coroutineDispatcher) {
        getApiResponseState(errorHandler = apiErrorHandler) {
            remoteApi.informCancelledPayment(
                orderId,
                uid
            )
        }
    }

}