package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.profile.remote.BasicProfileApi
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import fit.asta.health.data.profile.remote.model.CheckReferralDTO
import fit.asta.health.datastore.PrefManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepoImpl
@Inject constructor(
    private val profileApi: BasicProfileApi,
    private val prefManager: PrefManager,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepo {
    override suspend fun setProfilePresent() {
        prefManager.setScreenCode(3)
    }

    override suspend fun isProfileAvailable(userId: String): ResponseState<Boolean> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                profileApi.isUserProfileAvailable(userId).flag
            }
        }
    }

    override suspend fun createBasicProfile(basicProfileDTO: BasicProfileDTO): ResponseState<Boolean> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                profileApi.createBasicProfile(basicProfileDTO).data.flag
            }
        }
    }

    override suspend fun checkReferralCode(code: String): ResponseState<CheckReferralDTO> {
        return withContext(coroutineDispatcher) {
            getResponseState {
                profileApi.checkReferralCode(code)
            }
        }
    }
}