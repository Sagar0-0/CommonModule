package fit.asta.health.data.onboarding.remote

import fit.asta.health.data.onboarding.remote.modal.OnboardingDTO
import retrofit2.http.GET

interface OnboardingApi {
    @GET("onboarding/get/")
    suspend fun getData(): OnboardingDTO
}