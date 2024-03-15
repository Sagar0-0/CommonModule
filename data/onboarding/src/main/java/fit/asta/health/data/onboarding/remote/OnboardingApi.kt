package fit.asta.health.data.onboarding.remote

import fit.asta.health.common.utils.Response
import fit.asta.health.data.onboarding.remote.model.Onboarding
import retrofit2.http.GET

interface OnboardingApi {
    @GET("onboarding/get/")
    suspend fun getData(): Response<List<Onboarding>>
}