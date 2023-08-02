package fit.asta.health.payments.wallet.api

import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class WalletRestApi(baseUrl: String, client: OkHttpClient) : WalletApi {

    private val apiService: WalletApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(WalletApiService::class.java)

    override suspend fun getData(uid: String) = apiService.getData(uid)
}