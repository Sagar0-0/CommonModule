package fit.asta.health.onboarding.api

import fit.asta.health.onboarding.modal.OnboardingResponse
import retrofit2.http.GET

interface OnboardingApiService {
    @GET("onboarding/get/")
    suspend fun getData(): OnboardingResponse
}