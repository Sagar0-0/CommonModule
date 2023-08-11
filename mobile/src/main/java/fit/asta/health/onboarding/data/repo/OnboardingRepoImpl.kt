package fit.asta.health.onboarding.data.repo

import fit.asta.health.R
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.toResponseState
import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.remote.api.OnboardingApi
import fit.asta.health.onboarding.data.util.OnboardingDataMapper
import kotlinx.coroutines.flow.Flow

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val mapper: OnboardingDataMapper,
    private val prefManager: PrefManager
) : OnboardingRepo {
    override suspend fun getData(): ResponseState<List<OnboardingData>> {
        val response = remoteApi.getData()
        return mapper.mapToDomainModel(response).toResponseState()
    }

    override suspend fun dismissOnboarding() {
        prefManager.setPreferences(R.string.user_pref_onboarding_shown, true)
    }

    override fun getOnboardingShown(): Flow<Boolean> =
        prefManager.getPreferences(R.string.user_pref_onboarding_shown, false)

}

