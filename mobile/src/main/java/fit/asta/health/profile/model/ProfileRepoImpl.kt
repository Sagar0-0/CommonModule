package fit.asta.health.profile.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.domain.UserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: ProfileDataMapper,
) : ProfileRepo {
    override suspend fun getProfileData(uid: String): Flow<UserProfile> {
        return flow {
            emit(
                mapper.mapToDomainModel(remoteApi.getUserProfile(uid))
            )
        }
    }
}