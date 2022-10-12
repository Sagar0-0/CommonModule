package fit.asta.health.profile.model

import fit.asta.health.network.api.RemoteApis
import fit.asta.health.profile.model.domain.UserProfile


class ProfileRepoImpl(
    private val remoteApi: RemoteApis,
    private val mapper: ProfileDataMapper,
) : ProfileRepo {
    override suspend fun getProfileData(uid: String): UserProfile {
        return mapper.mapToDomainModel(remoteApi.getUserProfile(uid))
    }
}