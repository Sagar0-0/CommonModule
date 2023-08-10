package fit.asta.health.onboarding.data.repo

import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.modal.OnboardingData
import kotlinx.coroutines.flow.Flow

interface OnboardingRepo {
    suspend fun getData(): Flow<UiState<List<OnboardingData>>>
    suspend fun setOnboardingShown()
    suspend fun getOnboardingShown(): Flow<Boolean>
}