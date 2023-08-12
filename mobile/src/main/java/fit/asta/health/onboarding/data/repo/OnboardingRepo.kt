package fit.asta.health.onboarding.data.repo

import fit.asta.health.UserPreferences
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.onboarding.data.remote.modal.OnboardingDTO.OnboardingData
import kotlinx.coroutines.flow.Flow

interface OnboardingRepo {
    val userPreferences: Flow<UserPreferences>
    suspend fun getData(): ResponseState<List<OnboardingData>>
    suspend fun dismissOnboarding()
}