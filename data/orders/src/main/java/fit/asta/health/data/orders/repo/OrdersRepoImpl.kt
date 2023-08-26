package fit.asta.health.data.orders.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.orders.remote.OrdersApi
import fit.asta.health.data.orders.remote.model.OrdersDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepoImpl
@Inject constructor(
    private val ordersApi: OrdersApi,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OrdersRepo {
    override suspend fun getOrders(uid: String): ResponseState<OrdersDTO> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                ordersApi.getOrders(uid)
            }
        }
    }
}