package fit.asta.health.onboarding.data.remote.api

import fit.asta.health.onboarding.data.remote.modal.OnboardingDTO

interface OnboardingApi {
    suspend fun getData(): OnboardingDTO
}