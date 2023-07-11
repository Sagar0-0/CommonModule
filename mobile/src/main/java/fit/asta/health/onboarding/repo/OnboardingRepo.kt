package fit.asta.health.onboarding.repo

import fit.asta.health.network.data.ApiResponse
import fit.asta.health.onboarding.modal.OnboardingData

interface OnboardingRepo {
    suspend fun getData(): ApiResponse<List<OnboardingData>>
}