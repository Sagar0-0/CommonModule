package fit.asta.health.payments.sub.api

import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class SubscriptionRestApi(baseUrl: String, client: OkHttpClient) : SubscriptionApi {

    private val apiService: SubscriptionApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(SubscriptionApiService::class.java)

    override suspend fun getData(uid: String, country: String, date: String) =
        apiService.getData(uid, country, date)
}