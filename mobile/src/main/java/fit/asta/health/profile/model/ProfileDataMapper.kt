package fit.asta.health.profile.model

import fit.asta.health.profile.model.domain.*
import fit.asta.health.profile.model.network.*

class ProfileDataMapper {

    fun mapToDomainModel(userProfileRes: NetUserProfileRes): UserProfile {

        val profile = userProfileRes.userProfile
        val contact = mapContact(userProfileRes.userProfile.uid, profile.contact)
        val physique = mapPhysique(profile.physique)

        val ls = mapOf<UserPropertyType, ArrayList<HealthProperties>>()
        val health = mapOf<UserPropertyType, ArrayList<HealthProperties>>()
        val diet = mapOf<UserPropertyType, ArrayList<HealthProperties>>()

        ls.plus(Pair(UserPropertyType.SleepSchedule, mapSessionCard(profile.lifeStyle.sleep)))
        ls.plus(Pair(UserPropertyType.WorkSchedule, mapSessionCard(profile.lifeStyle.workingHours)))
        ls.plus(Pair(UserPropertyType.PhysActive, mapPlainCard(profile.lifeStyle.physicalActivity)))
        ls.plus(Pair(UserPropertyType.WorkStyle, mapPlainCard(profile.lifeStyle.workStyle)))
        ls.plus(Pair(UserPropertyType.CurActivities, mapChipCard(profile.lifeStyle.curActivities)))
        ls.plus(
            Pair(UserPropertyType.PrefActivities, mapChipCard(profile.lifeStyle.prefActivities))
        )
        ls.plus(Pair(UserPropertyType.LifeStyleTargets, mapChipCard(profile.lifeStyle.targets)))

        health.plus(Pair(UserPropertyType.Ailments, mapChipCard(profile.health.ailments)))
        health.plus(Pair(UserPropertyType.Medications, mapChipCard(profile.health.medications)))
        health.plus(Pair(UserPropertyType.Injuries, mapInjuryChipCard(profile.health.injuries)))
        health.plus(Pair(UserPropertyType.HealthTargets, mapChipCard(profile.health.targets)))

        diet.plus(Pair(UserPropertyType.DietPref, mapPrefPlainCard(profile.diet.preference)))
        diet.plus(Pair(UserPropertyType.NvDays, mapWeekChipCard(profile.diet.nonVegDays)))
        diet.plus(Pair(UserPropertyType.Cuisines, mapChipCard(profile.diet.cuisines)))
        diet.plus(Pair(UserPropertyType.FoodAllergies, mapChipCard(profile.diet.allergies)))

        return UserProfile(
            uid = profile.uid,
            contact = contact,
            physique = physique,
            lifestyle = ls,
            health = health,
            diet = diet
        )
    }

    private fun mapContact(uid: String, contact: NetContact) =
        Contact(
            id = uid,
            name = contact.name,
            dob = contact.dob,
            email = contact.email,
            phone = contact.ph,
            imgUrl = contact.url,
            address = Address(
                address = contact.address.address,
                street = contact.address.street,
                city = contact.address.city,
                country = contact.address.country,
                pin = contact.address.pin
            )
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

    private fun mapSessionCard(netSession: NetSession) =
        arrayListOf(
            HealthProperties(
                from = netSession.bedTime,
                to = netSession.wakeTime
            )
        )

    private fun mapPlainCard(properties: NetHealthProperties) =
        arrayListOf(
            HealthProperties(
                id = properties.id,
                type = properties.type,
                name = properties.name,
                code = properties.code,
                description = properties.description
            )
        )

    private fun mapPrefPlainCard(healthProperties: Int) =
        arrayListOf(
            HealthProperties(
                name = healthProperties.toString()
            )
        )

    private fun mapChipCard(properties: List<NetHealthProperties>) =
        properties.map { mapHealthProperties(it) }

    private fun mapInjuryChipCard(injuries: List<NetInjury>) =
        injuries.map { mapHealthProperties(it) }

    private fun mapWeekChipCard(weekDays: List<String>) =
        weekDays.map { mapHealthProperties(it) }

    private fun mapHealthProperties(netHealthProperties: NetHealthProperties) =
        HealthProperties(
            id = netHealthProperties.id,
            type = netHealthProperties.type,
            code = netHealthProperties.code,
            name = netHealthProperties.name,
            description = netHealthProperties.description
        )

    private fun mapHealthProperties(netHealthProperties: NetInjury) =
        HealthProperties(
            id = netHealthProperties.id,
            code = netHealthProperties.code,
            name = netHealthProperties.name,
            from = netHealthProperties.sinceWhen
        )

    private fun mapHealthProperties(netHealthProperties: String) =
        HealthProperties(
            name = netHealthProperties,
        )

    fun mapToNetworkModel(domainModel: UserProfile): NetUserProfile {

        val netContact = mapToNetContact(domainModel.contact)
        val netPhysique = mapToNetPhysique(domainModel.physique)

        val netHealth = NetHealth(
            ailments = arrayListOf(),
            medications = arrayListOf(),
            injuries = arrayListOf(),
            targets = arrayListOf()
        )

        val netLifeStyle = NetLifeStyle(
            sleep = NetSession(0.0, 0.0),
            workingHours = NetSession(0.0, 0.0),
            physicalActivity = NetHealthProperties("", 0, "", "", ""),
            workStyle = NetHealthProperties("", 0, "", "", ""),
            curActivities = arrayListOf(),
            prefActivities = arrayListOf(),
            targets = arrayListOf()
        )

        val netDiet = NetDiet(
            allergies = arrayListOf(),
            cuisines = arrayListOf(),
            nonVegDays = arrayListOf(),
            preference = 0,
            foodRestrictions = arrayListOf()
        )

        domainModel.health.map {
            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
        }

        domainModel.lifestyle.map {
            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
        }

        domainModel.diet.map {
            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
        }

        return NetUserProfile(
            uid = domainModel.uid,
            contact = netContact,
            physique = netPhysique,
            health = netHealth,
            lifeStyle = netLifeStyle,
            diet = netDiet,
        )
    }

    private fun mapToNetContact(contact: Contact): NetContact {
        return NetContact(
            name = contact.name,
            dob = contact.dob,
            email = contact.email,
            ph = contact.phone,
            url = contact.imgUrl,
            address = NetAddress(
                address = contact.address.address,
                street = contact.address.street,
                city = contact.address.city,
                country = contact.address.country,
                pin = contact.address.pin
            )
        )
    }

    private fun mapToNetPhysique(physique: Physique): NetPhysique {
        return NetPhysique(
            age = physique.age,
            bodyType = physique.bodyType,
            bmi = physique.bmi,
            gender = physique.gender,
            height = physique.height,
            isPregnant = physique.isPregnant,
            pregnancyWeek = physique.pregnancyWeek,
            weight = physique.weight
        )
    }

    private fun mapToNetPropertyType(
        type: UserPropertyType,
        properties: ArrayList<HealthProperties>,
        netHealth: NetHealth,
        netLifeStyle: NetLifeStyle,
        netDiet: NetDiet,
    ) {

        when (type) {
            is UserPropertyType.Ailments -> {
                properties.map {
                    netHealth.ailments.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.Cuisines -> {
                properties.map {
                    netDiet.cuisines.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.CurActivities -> {
                properties.map {
                    netLifeStyle.curActivities.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.DietPref -> {

            }
            is UserPropertyType.FoodAllergies -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.HealthTargets -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.Injuries -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.LifeStyleTargets -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.Medications -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.NvDays -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.PhysActive -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.PrefActivities -> {
                properties.map {
                    netDiet.allergies.add(mapToNetHealthProperties(it))
                }
            }
            is UserPropertyType.SleepSchedule -> {

            }
            is UserPropertyType.WorkSchedule -> {

            }
            is UserPropertyType.WorkStyle -> {

            }
            is UserPropertyType.NONE -> {

            }
        }
    }

    private fun mapToNetHealthProperties(it: HealthProperties) =
        NetHealthProperties(
            id = it.id,
            type = it.type,
            code = it.code,
            name = it.name,
            description = it.description
        )
}