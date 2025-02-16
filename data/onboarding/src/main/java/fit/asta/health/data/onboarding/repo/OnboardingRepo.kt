package fit.asta.health.data.onboarding.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.data.onboarding.remote.model.Onboarding
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.flow.Flow

interface OnboardingRepo {
    val userPreferences: Flow<UserPreferencesData>
    suspend fun getData(): ResponseState<List<Onboarding>>
}