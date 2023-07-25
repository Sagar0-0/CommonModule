package fit.asta.health.onboarding.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.modal.OnboardingData

interface OnboardingRepo {
    suspend fun getData(): ResponseState<List<OnboardingData>>
}