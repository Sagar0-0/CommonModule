package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.*
import fit.asta.health.profile.model.network.*
import fit.asta.health.utils.DomainMapper

class ProfileDataMapper : DomainMapper<NetUserProfileRes, UserProfile> {

    override fun mapToDomainModel(networkModel: NetUserProfileRes): UserProfile {

        val userProfileDao = networkModel.userProfile
        val contact = mapContact(networkModel.userProfile.uid, userProfileDao.contact)
        val physique = mapPhysique(userProfileDao.physique)

        val lifestyle = arrayListOf<ProfileItem>()
        val health = arrayListOf<ProfileItem>()
        val diet = arrayListOf<ProfileItem>()

        lifestyle.add(
            mapSessionCard(
                UserPropertyType.SleepSchedule,
                userProfileDao.lifeStyle.sleep
            )
        )
        lifestyle.add(
            mapSessionCard(
                UserPropertyType.WorkSchedule,
                userProfileDao.lifeStyle.workingHours
            )
        )
        lifestyle.add(
            mapPlainCard(
                UserPropertyType.PhysActive,
                userProfileDao.lifeStyle.physicalActivity
            )
        )
        lifestyle.add(mapPlainCard(UserPropertyType.WorkStyle, userProfileDao.lifeStyle.workStyle))
        lifestyle.add(
            mapChipCard(
                UserPropertyType.CurActivities,
                userProfileDao.lifeStyle.curActivities
            )
        )
        lifestyle.add(
            mapChipCard(
                UserPropertyType.PrefActivities,
                userProfileDao.lifeStyle.prefActivities
            )
        )
        lifestyle.add(
            mapChipCard(
                UserPropertyType.LifeStyleTargets,
                userProfileDao.lifeStyle.targets
            )
        )

        health.add(mapChipCard(UserPropertyType.Ailments, userProfileDao.health.ailments))
        health.add(mapChipCard(UserPropertyType.Medications, userProfileDao.health.medications))
        health.add(mapInjChipCard(UserPropertyType.Injuries, userProfileDao.health.injuries))
        health.add(mapChipCard(UserPropertyType.HealthTargets, userProfileDao.health.targets))

        diet.add(mapPrefPlainCard(UserPropertyType.DietPref, userProfileDao.diet.preference))
        diet.add(mapWeekChipCard(UserPropertyType.NvDays, userProfileDao.diet.nonVegDays))
        diet.add(mapChipCard(UserPropertyType.Cuisines, userProfileDao.diet.cuisines))
        diet.add(mapChipCard(UserPropertyType.FoodAllergies, userProfileDao.diet.allergies))

        return UserProfile(
            uid = userProfileDao.uid,
            contact = contact,
            physique = physique,
            lifestyle = lifestyle,
            health = health,
            diet = diet
        )
    }

    private fun mapContact(uid: String, contact: NetContact) =
        Contact(
            id = uid,
            name = contact.name,
            email = contact.email,
            phone = contact.ph,
            imgUrl = contact.url,
            address = contact.address.address
        )

    private fun mapPhysique(physique: NetPhysique) =
        Physique(
            age = physique.age,
            bodyType = physique.bodyType,
            bmi = physique.bmi,
            gender = physique.gender,
            height = physique.height,
            isPregnant = physique.isPregnant,
            pregnancyWeek = physique.pregnancyWeek,
            weight = physique.weight
        )

    private fun mapChipCard(
        userPropertyType: UserPropertyType,
        healthProperties: List<NetHealthProperties>
    ) =
        ProfileItem.ChipCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            value = healthProperties.map { mapHealthProperties(it) }
        )

    private fun mapHealthProperties(netHealthProperties: NetHealthProperties) =
        HealthProperties(
            id = netHealthProperties.id,
            type = netHealthProperties.type,
            code = netHealthProperties.code,
            name = netHealthProperties.name,
            description = netHealthProperties.description
        )

    private fun mapInjChipCard(userPropertyType: UserPropertyType, injuries: List<NetInjury>) =
        ProfileItem.ChipCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            //value = injuries
        )

    private fun mapWeekChipCard(userPropertyType: UserPropertyType, weekDays: List<String>) =
        ProfileItem.ChipCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            //value = weekDays
        )

    private fun mapPlainCard(
        userPropertyType: UserPropertyType,
        healthProperties: NetHealthProperties
    ) =
        ProfileItem.PlainCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            value = healthProperties.name
        )

    private fun mapPrefPlainCard(userPropertyType: UserPropertyType, healthProperties: Int) =
        ProfileItem.PlainCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            value = healthProperties.toString()
        )

    private fun mapSessionCard(userPropertyType: UserPropertyType, netSession: NetSession) =
        ProfileItem.SessionCard(
            title = userPropertyType.title,
            icon = userPropertyType.icon,
            startTime = netSession.bedTime.toString(),
            endTime = netSession.wakeTime.toString()
        )

    override fun mapFromDomainModel(domainModel: UserProfile): NetUserProfileRes {
        TODO("Not yet implemented")
    }
}