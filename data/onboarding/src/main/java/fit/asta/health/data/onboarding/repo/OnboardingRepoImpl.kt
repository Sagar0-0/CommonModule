package fit.asta.health.data.onboarding.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.getApiResponseState
import fit.asta.health.data.onboarding.remote.OnboardingApi
import fit.asta.health.datastore.PrefManager
import fit.asta.health.datastore.UserPreferencesData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnboardingRepoImpl
@Inject constructor(
    private val remoteApi: OnboardingApi,
    prefManager: PrefManager,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) : OnboardingRepo {

    override val userPreferences: Flow<UserPreferencesData> = prefManager.userData

    override suspend fun getData() = withContext(coroutineDispatcher) {
        getApiResponseState { remoteApi.getData() }
    }
}

