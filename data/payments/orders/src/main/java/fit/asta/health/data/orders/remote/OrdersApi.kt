package fit.asta.health.data.orders.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.orders.remote.model.OrderData
import fit.asta.health.data.orders.remote.model.OrderDetailData
import retrofit2.http.GET
import retrofit2.http.Query

typealias UserId = String
typealias OrderId = String

interface OrdersApi {

    @GET("payment/order/list/?")
    suspend fun getOrders(@Query("uid") uid: UserId): Response<List<OrderData>>

    @GET("payment/order/details/?")
    suspend fun getOrderDetails(@Query("oid") orderId: OrderId): Response<OrderDetailData>

}