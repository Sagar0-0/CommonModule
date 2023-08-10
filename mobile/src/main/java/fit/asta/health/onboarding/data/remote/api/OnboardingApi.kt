package fit.asta.health.onboarding.data.remote.api

import fit.asta.health.onboarding.data.remote.OnboardingDTO

interface OnboardingApi {
    suspend fun getData(): OnboardingDTO
}