package fit.asta.health.onboarding.api

import fit.asta.health.onboarding.modal.OnboardingResponse

interface OnboardingApi {
    suspend fun getData(): OnboardingResponse
}