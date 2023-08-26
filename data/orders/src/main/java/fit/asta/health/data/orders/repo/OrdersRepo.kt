package fit.asta.health.data.orders.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.orders.remote.model.OrdersDTO

interface OrdersRepo {
    suspend fun getOrders(uid: String): ResponseState<OrdersDTO>
}