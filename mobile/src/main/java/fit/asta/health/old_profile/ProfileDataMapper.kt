package fit.asta.health.old_profile

import fit.asta.health.old_profile.adapter.*
import fit.asta.health.old_profile.adapter.viewholders.ProfileTabType
import fit.asta.health.old_profile.data.userprofile.*

class ProfileDataMapper {

    fun toMap(userProfile: UserProfile): ProfileData {

        val physique = arrayListOf<ProfileItem>()
        val lifestyle = arrayListOf<ProfileItem>()
        val health = arrayListOf<ProfileItem>()
        val data = userProfile.data

        data.physique.forEach {
            if(it.type == ProfileItemType.PlainCard.value){
                val plainCardItem = PlainCardItem()
                plainCardItem.id = it.uid
                plainCardItem.label = it.ttl
                plainCardItem.itemValue = it.value
                plainCardItem.image = it.url
                plainCardItem.profileTabType = ProfileTabType.PhysiqueTab
                physique.add(plainCardItem)
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

        }

        return ProfileData(physique, lifestyle, health)
    }

    fun toMap(userId: String, profileData: ProfileData): Data {
        val contact = Contact()
        val physiques = ArrayList<Physique>()
        val lifestyle = Lifestyle()
        val healthTargets = ArrayList<Health>()

        profileData.lifestyle.map {
            when (it) {
                is ChipCardItem -> {
                    val chipCard = ChipCard()
                    chipCard.ttl = it.label
                    chipCard.type = it.profileType.value
                    chipCard.uid = it.id
                    chipCard.url = it.image
                    chipCard.value = it.value
                    lifestyle.chipCards += chipCard
                }
                is PlainCardItem -> {
                    val plainCard = PlainCard()
                    plainCard.ttl = it.label
                    plainCard.type = it.profileType.value
                    plainCard.uid = it.id
                    plainCard.url = it.image
                    plainCard.value = it.updatedValue
                    lifestyle.plainCard = plainCard
                }
                is SleepScheduleItem -> {
                    val scheduleCard = Schedule()
                    scheduleCard.ttl = it.label
                    scheduleCard.type = it.profileType.value
                    scheduleCard.bedTime = it.bedTime
                    scheduleCard.wakeUp = it.wakeUpTime
                    lifestyle.schedule = scheduleCard
                }
            }
        }

        profileData.physique.map {
            when (it) {

                is PlainCardItem -> {
                    val physique = Physique()
                    physique.ttl = it.label
                    physique.type = it.profileType.value
                    physique.uid = it.id
                    physique.url = it.image
                    physique.value = it.updatedValue
                    physiques += physique
                }
                is BodyTypeItem -> { }
            }
        }

        profileData.health.map {
            when (it) {
                is ChipCardItem -> {
                    val health = Health()
                    health.ttl = it.label
                    health.type = it.profileType.value
                    health.uid = it.id
                    health.url = it.image
                    health.value = it.value
                    healthTargets += health
                }
                is PlainCardItem -> { }
            }
        }

        return Data(contact, healthTargets, lifestyle, physiques, userId)
    }

}