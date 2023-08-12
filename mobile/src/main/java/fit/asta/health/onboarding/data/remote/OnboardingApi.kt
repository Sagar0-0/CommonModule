package fit.asta.health.onboarding.data.remote

import fit.asta.health.onboarding.data.remote.modal.OnboardingDTO
import retrofit2.http.GET

interface OnboardingApi {
    @GET("onboarding/get/")
    suspend fun getData(): OnboardingDTO
}