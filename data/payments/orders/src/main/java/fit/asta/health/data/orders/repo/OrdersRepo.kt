package fit.asta.health.data.orders.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.orders.remote.OrderId
import fit.asta.health.data.orders.remote.UserId
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.data.orders.remote.model.OrderDetailData

interface OrdersRepo {
    suspend fun getOrders(uid: UserId): ResponseState<List<OrderData>>
    suspend fun getOrderDetail(orderId: OrderId): ResponseState<OrderDetailData>
}