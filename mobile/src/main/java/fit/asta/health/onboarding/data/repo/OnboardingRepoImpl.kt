package fit.asta.health.onboarding.data.repo

import fit.asta.health.UserPreferences
import fit.asta.health.common.utils.CoroutineDispatcherProvider
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.UserPreferencesData
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.common.utils.toResponseState
import fit.asta.health.onboarding.data.model.OnboardingData
import fit.asta.health.onboarding.data.remote.OnboardingApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val prefManager: PrefManager,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OnboardingRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getResponseState { remoteApi.getData().data }
    }

    override suspend fun dismissOnboarding() {
        prefManager.setOnboardingShown()
    }
}

