package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.*
import fit.asta.health.profile.model.network.UserProfileResponse
import fit.asta.health.utils.DomainMapper

class ProfileDataMapper : DomainMapper<UserProfileResponse, UserProfile> {
    override fun mapToDomainModel(networkModel: UserProfileResponse): UserProfile {
        /*
        return UserProfile(
            uid = networkModel.data["uid"].toString(),
            profileData = networkModel.data["cont"] as Map<String, Any>,
            physic = networkModel.data["phq"] as Map<String, Any>,
            lifestyle = networkModel.data["ls"] as Map<String, Any>,
            health = networkModel.data["hlt"] as Map<String, Any>,
            diet = networkModel.data["diet"] as Map<String, Any>
        )
        */

        val userProfileDao = networkModel.userProfile

        val contact = ContactItem(
            id = userProfileDao.uid,
            name = userProfileDao.contact.name,
            email = userProfileDao.contact.email,
            phone = userProfileDao.contact.ph,
            imgUrl = userProfileDao.contact.url,
            address = userProfileDao.contact.address.address
        )

        val physique = arrayListOf<ProfileItem>()
        val lifestyle = arrayListOf<ProfileItem>()
        val health = arrayListOf<ProfileItem>()

        val plainCardItem = PlainCardItem()
        plainCardItem.id = ""
        plainCardItem.label = "Age"
        plainCardItem.itemValue = userProfileDao.physique.age.toString()
        plainCardItem.image = ""
        plainCardItem.profileTabType = ProfileTabType.PhysiqueTab
        physique.add(plainCardItem)

        /*data.physique.forEach {
            if(it.type == ProfileItemType.PlainCard.value){

            }
            else if(it.type == ProfileItemType.BodyTypeCard.value){
                val bodyTypeItem = BodyTypeItem()
                bodyTypeItem.id = it.uid
                bodyTypeItem.label = it.ttl
                bodyTypeItem.bodyTypeValue = it.value
                bodyTypeItem.image = it.url
                bodyTypeItem.profileTabType = ProfileTabType.PhysiqueTab
                physique.add(bodyTypeItem)
            }
        }

        data.lifestyle.let {
            val schedule = SleepScheduleItem()
            schedule.label = it.schedule.ttl
            schedule.bedTime = it.schedule.bedTime
            schedule.wakeUpTime = it.schedule.wakeUp
            schedule.profileTabType = ProfileTabType.LifeStyleTab
            lifestyle.add(schedule)
            val plainCardItem = PlainCardItem()
            plainCardItem.id = it.plainCard.uid
            plainCardItem.label = it.plainCard.ttl
            plainCardItem.itemValue = it.plainCard.value
            plainCardItem.image = it.plainCard.url
            plainCardItem.profileTabType = ProfileTabType.LifeStyleTab
            lifestyle.add(plainCardItem)
            it.chipCards.forEach { chip ->
                val chipCardItem = ChipCardItem()
                chipCardItem.id = chip.uid
                chipCardItem.label = chip.ttl
                chipCardItem.value = chip.value
                chipCardItem.image = chip.url
                chipCardItem.profileTabType = ProfileTabType.LifeStyleTab
                lifestyle.add(chipCardItem)
            }
        }

        data.health.forEach {
            val chipCardItem = ChipCardItem()
            chipCardItem.id = it.uid
            chipCardItem.label = it.ttl
            chipCardItem.value = it.value
            chipCardItem.image = it.url
            chipCardItem.profileTabType = ProfileTabType.HealthTargetsTab
            health.add(chipCardItem)
        }*/

        return UserProfile(
            uid = userProfileDao.uid,
            contact = contact,
            physique = arrayListOf<ProfileItem>(),
            lifestyle = arrayListOf<ProfileItem>(),
            health = arrayListOf<ProfileItem>(),
            diet = arrayListOf<ProfileItem>()
        )
    }

    override fun mapFromDomainModel(domainModel: UserProfile): UserProfileResponse {
        TODO("Not yet implemented")
    }
}