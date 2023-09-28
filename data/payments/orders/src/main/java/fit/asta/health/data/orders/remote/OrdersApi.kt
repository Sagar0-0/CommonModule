package fit.asta.health.data.orders.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.orders.remote.model.OrderData
import retrofit2.http.GET
import retrofit2.http.Query

interface OrdersApi {

    @GET("payment/order/list/?")
    suspend fun getOrders(@Query("uid") uid: String): Response<List<OrderData>>
}