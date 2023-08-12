package fit.asta.health.onboarding.data.repo

import fit.asta.health.UserPreferences
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.toResponseState
import fit.asta.health.onboarding.data.model.OnboardingData
import fit.asta.health.onboarding.data.remote.OnboardingApi
import kotlinx.coroutines.flow.Flow

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val prefManager: PrefManager
) : OnboardingRepo {

    override val userPreferences: Flow<UserPreferences> = prefManager.userData

    override suspend fun getData(): ResponseState<List<OnboardingData>> {
        val response = remoteApi.getData()
        return response.data.toResponseState()
    }

    override suspend fun dismissOnboarding() {
        prefManager.setOnboardingShown()
    }
}

