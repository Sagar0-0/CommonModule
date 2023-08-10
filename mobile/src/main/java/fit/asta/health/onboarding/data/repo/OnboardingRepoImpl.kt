package fit.asta.health.onboarding.data.repo

import fit.asta.health.R
import fit.asta.health.common.utils.PrefManager
import fit.asta.health.common.utils.UiState
import fit.asta.health.onboarding.data.modal.OnboardingData
import fit.asta.health.onboarding.data.remote.api.OnboardingApi
import fit.asta.health.onboarding.data.util.OnboardingDataMapper
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class OnboardingRepoImpl(
    private val remoteApi: OnboardingApi,
    private val mapper: OnboardingDataMapper,
    private val prefManager: PrefManager
) : OnboardingRepo {
    override suspend fun getData(): Flow<UiState<List<OnboardingData>>> = callbackFlow {
        trySend(
            try {
                val response = remoteApi.getData()
                UiState.Success(mapper.mapToDomainModel(response))
            } catch (e: Exception) {
                UiState.Error(getStringFromException(e))
            }
        )
        awaitClose { close() }
    }

    override suspend fun setOnboardingShown() {
        prefManager.setPreferences(R.string.user_pref_onboarding_shown, true)
    }

    override suspend fun getOnboardingShown(): Flow<Boolean> =
        prefManager.getPreferences(R.string.user_pref_onboarding_shown, false)

}

fun getStringFromException(e: Exception): Int {
    return when (e.message) {
        else -> R.string.unknown_error
    }
}