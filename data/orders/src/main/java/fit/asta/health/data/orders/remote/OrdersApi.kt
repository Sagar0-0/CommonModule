package fit.asta.health.data.orders.remote

import fit.asta.health.data.orders.remote.model.OrdersDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersApi {

    @GET("payment/order/list/?")
    fun getOrders(@Query("uid") uid: String): OrdersDTO
}