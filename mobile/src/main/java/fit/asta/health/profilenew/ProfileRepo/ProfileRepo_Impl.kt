package fit.asta.health.profilenew.ProfileRepo

import fit.asta.health.network.api.ApiService
import fit.asta.health.profilenew.data.ProileData
import fit.asta.health.profilenew.ProfileDaoMapper


class ProfileRepo_Impl(
    private val apiService: ApiService,
    private val mapper: ProfileDaoMapper,
) : ProfileRepo {
    override suspend fun getProfileData(uid: String): ProileData {
        return mapper.mapToDomainModel(apiService.getProfileData(uid))
    }


}