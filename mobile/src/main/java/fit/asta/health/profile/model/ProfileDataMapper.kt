package fit.asta.health.profile.model


class ProfileDataMapper {

//    fun mapToDomainModel(userProfileRes: NetUserProfileRes): UserProfile {
//
//        val profile = userProfileRes.userProfile
//        val contact = mapContact(userProfileRes.userProfile.uid, profile.contact)
//        val physique = mapPhysique(profile.physique)
//
//        val ls = mutableMapOf<UserPropertyType, ArrayList<HealthProperties>>()
//        val health = mutableMapOf<UserPropertyType, ArrayList<HealthProperties>>()
//        val diet = mutableMapOf<UserPropertyType, ArrayList<HealthProperties>>()
//
//        ls[UserPropertyType.SleepSchedule] = mapSessionCard(profile.lifeStyle.sleep)
//        ls[UserPropertyType.WorkSchedule] = mapSessionCard(profile.lifeStyle.workingHours)
//        ls[UserPropertyType.PhysActive] = mapPlainCard(profile.lifeStyle.physicalActivity)
//        ls[UserPropertyType.WorkStyle] = mapPlainCard(profile.lifeStyle.workStyle)
//        ls[UserPropertyType.CurActivities] = mapChipCard(profile.lifeStyle.curActivities!!)
//        ls[UserPropertyType.PrefActivities] = mapChipCard(profile.lifeStyle.prefActivities!!)
//        ls[UserPropertyType.LifeStyleTargets] = mapChipCard(profile.lifeStyle.lifeStyleTargets!!)
//
//        health[UserPropertyType.Ailments] = mapChipCard(profile.health.ailments!!)
//        health[UserPropertyType.Medications] = mapChipCard(profile.health.medications!!)
//        health[UserPropertyType.Injuries] = mapInjuryChipCard(profile.health.injuries!!)
//        health[UserPropertyType.HealthTargets] = mapChipCard(profile.health.targets!!)
//
//        diet[UserPropertyType.DietPref] = mapChipCard(profile.diet.preference!!)
//        diet[UserPropertyType.NvDays] = mapChipCard(profile.diet.nonVegDays!!)
//        diet[UserPropertyType.Cuisines] = mapChipCard(profile.diet.cuisines!!)
//        diet[UserPropertyType.FoodAllergies] = mapChipCard(profile.diet.allergies!!)
//
//        return UserProfile(
//            uid = profile.uid,
//            contact = contact,
//            physique = physique,
//            lifeStyle = ls,
//            health = health,
//            diet = diet
//        )
//    }
//
//    private fun mapContact(uid: String, contact: NetContact) = Contact(
//        id = uid,
//        name = contact.name,
//        dob = contact.dob,
//        email = contact.email,
//        phone = contact.ph,
//        url = contact.url,
//        address = Address(
//            address = contact.address.address,
//            street = contact.address.street,
//            city = contact.address.city,
//            country = contact.address.country,
//            pin = contact.address.pin
//        )
//    )
//
//    private fun mapPhysique(physique: NetPhysique) = Physique(
//        age = physique.age,
//        bodyType = physique.bodyType,
//        bmi = physique.bmi,
//        gender = physique.gender,
//        height = physique.height,
//        isPregnant = physique.isPregnant,
//        pregnancyWeek = physique.pregnancyWeek,
//        weight = physique.weight
//    )
//
//    private fun mapSessionCard(netSession: NetSession) = arrayListOf(
//        HealthProperties(
//            from = netSession.from, to = netSession.to
//        )
//    )
//
//    private fun mapPlainCard(properties: NetHealthProperties) = arrayListOf(
//        HealthProperties(
//            id = properties.id,
//            type = properties.type,
//            name = properties.name,
//            code = properties.code,
//            description = properties.description
//        )
//    )
//
//    private fun mapPrefPlainCard(healthProperties: Int) = arrayListOf(
//        HealthProperties(
//            name = healthProperties.toString()
//        )
//    )
//
//    private fun mapChipCard(properties: ArrayList<NetHealthProperties>): ArrayList<HealthProperties> {
//
//        val arrayList = ArrayList<HealthProperties>()
//        properties.map { arrayList.add(mapHealthProperties(it)) }
//        return arrayList
//    }
//
//    private fun mapInjuryChipCard(injuries: ArrayList<NetInjury>): ArrayList<HealthProperties> {
//
//        val arrayList = ArrayList<HealthProperties>()
//        injuries.map { arrayList.add(mapHealthProperties(it)) }
//        return arrayList
//    }
//
//    private fun mapWeekChipCard(weekDays: ArrayList<String>): ArrayList<HealthProperties> {
//
//        val arrayList = ArrayList<HealthProperties>()
//        weekDays.map { arrayList.add(mapHealthProperties(it)) }
//        return arrayList
//    }
//
//    private fun mapHealthProperties(netHealthProperties: NetHealthProperties) = HealthProperties(
//        id = netHealthProperties.id,
//        type = netHealthProperties.type,
//        code = netHealthProperties.code,
//        name = netHealthProperties.name,
//        description = netHealthProperties.description
//    )
//
//    private fun mapHealthProperties(netHealthProperties: NetInjury) = HealthProperties(
//        id = netHealthProperties.id,
//        code = netHealthProperties.code,
//        name = netHealthProperties.name,
//        from = netHealthProperties.sinceWhen
//    )
//
//    private fun mapHealthProperties(netHealthProperties: String) = HealthProperties(
//        name = netHealthProperties,
//    )
//
//    fun mapToNetworkModel(domainModel: UserProfile): NetUserProfile {
//
//        val netContact = mapToNetContact(domainModel.contact)
//        val netPhysique = mapToNetPhysique(domainModel.physique)
//
//        val netHealth = NetHealth(
//            ailments = arrayListOf(),
//            medications = arrayListOf(),
//            injuries = arrayListOf(),
//            lifeStyleTargets = arrayListOf()
//        )
//
//        val netLifeStyle = NetLifeStyle(
//            sleep = NetSession(0.0, 0.0),
//            workingHours = NetSession(0.0, 0.0),
//            physicalActivity = NetHealthProperties("", 0, "", "", ""),
//            workStyle = NetHealthProperties("", 0, "", "", ""),
//            curActivities = arrayListOf(),
//            prefActivities = arrayListOf(),
//            lifeStyleTargets = arrayListOf()
//        )
//
//        val netDiet = NetDiet(
//            allergies = arrayListOf(),
//            cuisines = arrayListOf(),
//            nonVegDays = arrayListOf(),
//            preference = 0,
//            foodRestrictions = arrayListOf()
//        )
//
//        domainModel.health.map {
//            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
//        }
//
//        domainModel.lifestyle.map {
//            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
//        }
//
//        domainModel.diet.map {
//            mapToNetPropertyType(it.key, it.value, netHealth, netLifeStyle, netDiet)
//        }
//
//        return NetUserProfile(
//            uid = domainModel.uid,
//            contact = netContact,
//            physique = netPhysique,
//            health = netHealth,
//            lifeStyle = netLifeStyle,
//            diet = netDiet,
//        )
//    }
//
//    private fun mapToNetContact(contact: Contact): NetContact {
//        return NetContact(
//            name = contact.name,
//            dob = contact.dob,
//            email = contact.email,
//            ph = contact.phone,
//            url = contact.imgUrl,
//            address = NetAddress(
//                address = contact.address.address,
//                street = contact.address.street,
//                city = contact.address.city,
//                country = contact.address.country,
//                pin = contact.address.pin
//            )
//        )
//    }
//
//    private fun mapToNetPhysique(physique: Physique): NetPhysique {
//        return NetPhysique(
//            age = physique.age,
//            bodyType = physique.bodyType,
//            bmi = physique.bmi,
//            gender = physique.gender,
//            height = physique.height,
//            isPregnant = physique.isPregnant,
//            pregnancyWeek = physique.pregnancyWeek,
//            weight = physique.weight
//        )
//    }
//
//    private fun mapToNetPropertyType(
//        type: UserPropertyType,
//        properties: ArrayList<HealthProperties>,
//        netHealth: NetHealth,
//        netLifeStyle: NetLifeStyle,
//        netDiet: NetDiet,
//    ) {
//
//        when (type) {
//            is UserPropertyType.Ailments -> {
//                properties.map {
//                    netHealth.ailments.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.Cuisines -> {
//                properties.map {
//                    netDiet.cuisines.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.CurActivities -> {
//                properties.map {
//                    netLifeStyle.curActivities.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.DietPref -> {
//                netDiet.preference = properties[0].name.toInt()
//            }
//            is UserPropertyType.FoodAllergies -> {
//                properties.map {
//                    netDiet.allergies.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.HealthTargets -> {
//                properties.map {
//                    netHealth.lifeStyleTargets.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.Injuries -> {
//                properties.map {
//                    netHealth.injuries.add(mapToNetInjuries(it))
//                }
//            }
//            is UserPropertyType.LifeStyleTargets -> {
//                properties.map {
//                    netLifeStyle.lifeStyleTargets.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.Medications -> {
//                properties.map {
//                    netHealth.medications.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.NvDays -> {
//                properties.map {
//                    netDiet.nonVegDays.add(it.name)
//                }
//            }
//            is UserPropertyType.PhysActive -> {
//                netLifeStyle.physicalActivity = mapToNetHealthProperties(properties[0])
//            }
//            is UserPropertyType.PrefActivities -> {
//                properties.map {
//                    netLifeStyle.prefActivities.add(mapToNetHealthProperties(it))
//                }
//            }
//            is UserPropertyType.SleepSchedule -> {
//                netLifeStyle.sleep = mapToNetSession(properties[0])
//            }
//            is UserPropertyType.WorkSchedule -> {
//                netLifeStyle.workingHours = mapToNetSession(properties[0])
//            }
//            is UserPropertyType.WorkStyle -> {
//                netLifeStyle.workStyle = mapToNetHealthProperties(properties[0])
//            }
//            is UserPropertyType.NONE -> {
//                //Log.DEBUG("User Profile Health Properties not mapped!")
//            }
//        }
//    }
//
//    private fun mapToNetHealthProperties(it: HealthProperties) = NetHealthProperties(
//        id = it.id, type = it.type, code = it.code, name = it.name, description = it.description
//    )
//
//    private fun mapToNetSession(it: HealthProperties) = NetSession(
//        from = it.from, to = it.to
//    )
//
//    private fun mapToNetInjuries(it: HealthProperties) = NetInjury(
//        id = it.id, name = it.name, code = it.code, sinceWhen = it.from
//    )
}