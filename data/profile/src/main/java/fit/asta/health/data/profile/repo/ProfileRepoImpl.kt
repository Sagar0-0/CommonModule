package fit.asta.health.data.profile.repo

import fit.asta.health.common.utils.IODispatcher
import fit.asta.health.common.utils.ResponseState
import fit.asta.health.common.utils.getResponseState
import fit.asta.health.data.profile.remote.BasicProfileApi
import fit.asta.health.data.profile.remote.model.BasicProfileDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileRepoImpl
@Inject constructor(
    private val profileApi: BasicProfileApi,
    @IODispatcher private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProfileRepo {
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
}