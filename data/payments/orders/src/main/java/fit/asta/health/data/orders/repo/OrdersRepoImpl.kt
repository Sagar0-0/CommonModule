package fit.asta.health.data.orders.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.orders.remote.OrderId
import fit.asta.health.data.orders.remote.OrdersApi
import fit.asta.health.data.orders.remote.UserId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.data.orders.remote.model.OrderDetailData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrdersRepoImpl
@Inject constructor(
    private val ordersApi: OrdersApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : OrdersRepo {


    override suspend fun getOrders(uid: UserId): ResponseState<List<OrderData>> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                ordersApi.getOrders(uid)
            }
        }
    }

    override suspend fun getOrderDetail(orderId: OrderId): ResponseState<OrderDetailData> {
        return withContext(coroutineDispatcher) {
            getApiResponseState {
                ordersApi.getOrderDetails(orderId)
            }
        }
    }
}