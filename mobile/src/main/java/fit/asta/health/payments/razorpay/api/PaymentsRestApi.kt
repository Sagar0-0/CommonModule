package fit.asta.health.payments.razorpay.api

import fit.asta.health.common.utils.NetworkUtil
import fit.asta.health.payments.razorpay.model.OrderRequest
import fit.asta.health.payments.razorpay.model.OrderResponse
import fit.asta.health.payments.razorpay.model.PaymentResponse
import okhttp3.OkHttpClient

class PaymentsRestApi(baseUrl: String, client: OkHttpClient) : PaymentsApi {

    private val apiService: PaymentsApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(PaymentsApiService::class.java)

    override suspend fun createOrder(data: OrderRequest): OrderResponse =
        apiService.createOrder(data)

    override suspend fun verifyAndUpdateProfile(paymentId: String, uid: String): PaymentResponse =
        apiService.verifyAndUpdateProfile(paymentId, uid)

}