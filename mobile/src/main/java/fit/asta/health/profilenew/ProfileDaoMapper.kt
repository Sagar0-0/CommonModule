package fit.asta.health.profilenew

import fit.asta.health.profilenew.data.ProileData
import fit.asta.health.profilenew.apiData.ProfileDao
import fit.asta.health.profilenew.data.MainProfile
import fit.asta.health.utils.DomainMapper

class ProfileDaoMapper:DomainMapper<ProfileDao,ProileData> {
    override fun mapToDomainModel(networkModel: ProfileDao): ProileData {
        return ProileData(
            uid = networkModel.data["uid"].toString(),
            profileData = networkModel.data["cont"] as Map<String, Any>,
            physic = networkModel.data["phq"] as Map<String, Any>,
            lifestyle = networkModel.data["ls"] as Map<String, Any>,
            health = networkModel.data["hlt"] as Map<String, Any>,
            diet = networkModel.data["diet"] as Map<String, Any>
        )
    }

    override fun mapFromDomainModel(domainModel: ProileData): ProfileDao {
        TODO("Not yet implemented")
    }
}