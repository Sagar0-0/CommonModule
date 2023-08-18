package fit.asta.health.onboarding.data.repo

import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.UserPreferencesData
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.di.IODispatcher
import fit.asta.health.onboarding.data.remote.OnboardingApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val prefManager: PrefManager,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OnboardingRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.getData().data }
    }

    override suspend fun dismissOnboarding() {
        prefManager.setOnboardingShown()
    }
}

