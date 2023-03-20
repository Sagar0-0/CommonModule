package fit.asta.health.profile.model

import fit.asta.health.network.data.Status
import fit.asta.health.profile.model.api.ProfileApi
import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.NetHealthPropertiesRes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class ProfileRepoImpl(
    private val remoteApi: ProfileApi,
    private val mapper: ProfileDataMapper,
) : ProfileRepo {

    override suspend fun getUserProfile(uid: String): Flow<UserProfile> {
        return flow {
            emit(remoteApi.getUserProfile(uid).userProfile)
        }
    }

    override suspend fun updateUserProfile(userProfile: UserProfile): Flow<Status> {
        return flow {
            emit(remoteApi.updateUserProfile(userProfile))
        }
    }

    override suspend fun isUserProfileAvailable(userId: String): Flow<Boolean> {
        return flow {
            emit(remoteApi.isUserProfileAvailable(userId).flag)
        }
    }

    override suspend fun getHealthProperties(propertyType: String): Flow<NetHealthPropertiesRes> {
        return flow {
            emit(remoteApi.getHealthProperties(propertyType))
        }
    }

}