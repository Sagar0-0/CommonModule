package fit.asta.health.onboarding.data.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.data.modal.OnboardingData
import kotlinx.coroutines.flow.Flow

interface OnboardingRepo {
    suspend fun getData(): ResponseState<List<OnboardingData>>
    suspend fun dismissOnboarding()
    fun getOnboardingShown(): Flow<Boolean>
}