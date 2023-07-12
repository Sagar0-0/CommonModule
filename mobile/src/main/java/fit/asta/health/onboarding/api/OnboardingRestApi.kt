package fit.asta.health.onboarding.api

import fit.asta.health.common.utils.NetworkUtil
import okhttp3.OkHttpClient

class OnboardingRestApi(baseUrl: String, client: OkHttpClient) : OnboardingApi {

    private val apiService: OnboardingApiService =
        NetworkUtil.getRetrofit(baseUrl = baseUrl, client = client)
            .create(OnboardingApiService::class.java)

    override suspend fun getData() = apiService.getData()
}