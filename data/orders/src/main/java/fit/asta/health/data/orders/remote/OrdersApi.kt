package fit.asta.health.data.orders.remote

import fit.asta.health.data.orders.remote.model.OrdersDTO

interface OrdersApi {

    fun getOrders(uid: String): OrdersDTO
}