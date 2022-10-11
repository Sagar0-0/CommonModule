package fit.asta.health.profile.model

import fit.asta.health.network.api.ApiService
import fit.asta.health.profile.model.domain.UserProfile


class ProfileRepoImpl(
    private val apiService: ApiService,
    private val mapper: ProfileDataMapper,
) : ProfileRepo {
    override suspend fun getProfileData(uid: String): UserProfile {
        return mapper.mapToDomainModel(apiService.getProfileData(uid))
    }
}