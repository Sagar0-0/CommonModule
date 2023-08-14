package fit.asta.health.onboarding.data.repo

import fit.asta.health.UserPreferences
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UserPreferencesData
import fit.asta.health.onboarding.data.model.OnboardingData
import kotlinx.coroutines.flow.Flow

interface OnboardingRepo {
    val userPreferences: Flow<UserPreferencesData>
    suspend fun getData(): ResponseState<List<OnboardingData>>
    suspend fun dismissOnboarding()
}