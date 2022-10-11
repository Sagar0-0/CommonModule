package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.UserProfile
import fit.asta.health.profile.model.network.ProfileDao
import fit.asta.health.utils.DomainMapper

class ProfileDataMapper:DomainMapper<ProfileDao,UserProfile> {
    override fun mapToDomainModel(networkModel: ProfileDao): UserProfile {
        return UserProfile(
            uid = networkModel.data["uid"].toString(),
            profileData = networkModel.data["cont"] as Map<String, Any>,
            physic = networkModel.data["phq"] as Map<String, Any>,
            lifestyle = networkModel.data["ls"] as Map<String, Any>,
            health = networkModel.data["hlt"] as Map<String, Any>,
            diet = networkModel.data["diet"] as Map<String, Any>
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): ProfileDao {
        TODO("Not yet implemented")
    }
}