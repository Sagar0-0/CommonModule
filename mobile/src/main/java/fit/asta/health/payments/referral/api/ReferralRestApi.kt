package fit.asta.health.payments.referral.api

import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class ReferralRestApi(baseUrl: String, client: OkHttpClient) : ReferralApi {

    private val apiService: ReferralApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(ReferralApiService::class.java)

    override suspend fun getData(uid: String) = apiService.getData(uid)
    override suspend fun applyCode(refCode: String, uid: String) =
        apiService.applyCode(refCode, uid)
}