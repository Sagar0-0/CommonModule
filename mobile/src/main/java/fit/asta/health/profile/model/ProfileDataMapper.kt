package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.Contact
import fit.asta.health.profile.model.domain.Physique
import fit.asta.health.profile.model.domain.ProfileItem
import fit.asta.health.profile.model.domain.UserProfile
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

        lifestyle.add(mapSessionCard(userProfileDao.lifeStyle.sleep))
        lifestyle.add(mapSessionCard(userProfileDao.lifeStyle.workingHours))
        lifestyle.add(mapPlainCard(userProfileDao.lifeStyle.physicalActivity))
        lifestyle.add(mapPlainCard(userProfileDao.lifeStyle.workStyle))
        lifestyle.add(mapChipCard(userProfileDao.lifeStyle.curActivities))
        lifestyle.add(mapChipCard(userProfileDao.lifeStyle.prefActivities))
        lifestyle.add(mapChipCard(userProfileDao.lifeStyle.targets))

        health.add(mapChipCard(userProfileDao.health.ailments))
        health.add(mapChipCard(userProfileDao.health.medications))
        health.add(mapInjChipCard(userProfileDao.health.injuries))
        health.add(mapChipCard(userProfileDao.health.targets))

        diet.add(mapPrefPlainCard(userProfileDao.diet.preference))
        diet.add(mapWeekChipCard(userProfileDao.diet.nonVegDays))
        diet.add(mapChipCard(userProfileDao.diet.cuisines))
        diet.add(mapChipCard(userProfileDao.diet.allergies))

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

    private fun mapChipCard(healthProperties: List<NetHealthProperties>) =
        ProfileItem.ChipCard(

        )

    private fun mapInjChipCard(healthProperties: List<NetInjury>) =
        ProfileItem.ChipCard(

        )

    private fun mapWeekChipCard(healthProperties: List<String>) =
        ProfileItem.ChipCard(

        )

    private fun mapPlainCard(healthProperties: NetHealthProperties) =
        ProfileItem.PlainCard(

        )

    private fun mapPrefPlainCard(healthProperties: Int) =
        ProfileItem.PlainCard(

        )

    private fun mapSessionCard(healthProperties: NetSession) =
        ProfileItem.SessionCard(

        )

    override fun mapFromDomainModel(domainModel: UserProfile): NetUserProfileRes {
        TODO("Not yet implemented")
    }
}