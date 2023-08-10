package fit.asta.health.onboarding.data.remote.api

import fit.asta.health.onboarding.data.remote.OnboardingDTO
import retrofit2.http.GET

interface OnboardingApiService {
    @GET("onboarding/get/")
    suspend fun getData(): OnboardingDTO
}