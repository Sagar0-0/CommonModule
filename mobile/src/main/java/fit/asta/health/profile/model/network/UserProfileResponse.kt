package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

/*
data class ProfileDao(
    val status: Status,
    val `data`: Map<String, Any>
)
*/

data class UserProfileResponse(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val userProfile: UserProfileDao
)

data class UserProfileDao(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("cont")
    val contact: Contact,
    @SerializedName("phq")
    val physique: Physique,
    @SerializedName("diet")
    val diet: Diet,
    @SerializedName("hlt")
    val health: Health,
    @SerializedName("ls")
    val lifeStyle: LifeStyle
)

data class Contact(
    @SerializedName("adr")
    val address: Address,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ph")
    val ph: String,
    @SerializedName("url")
    val url: String
)

data class Address(
    @SerializedName("adr1")
    val address: String,
    @SerializedName("cnt")
    val country: String,
    @SerializedName("cty")
    val city: String,
    @SerializedName("pin")
    val pin: String,
    @SerializedName("st")
    val street: String
)

data class Physique(
    @SerializedName("age")
    val age: Int,
    @SerializedName("bdyType")
    val bodyType: Int,
    @SerializedName("bmi")
    val bmi: Int,
    @SerializedName("gen")
    val gender: String,
    @SerializedName("ht")
    val height: Int,
    @SerializedName("prg")
    val isPregnant: Boolean,
    @SerializedName("prgWk")
    val pregnancyWeek: Int,
    @SerializedName("wt")
    val weight: Int
)

data class Diet(
    @SerializedName("alg")
    val allergies: List<HealthProperties>,
    @SerializedName("cns")
    val cuisines: List<HealthProperties>,
    @SerializedName("nVeg")
    val nonVegDays: List<String>,
    @SerializedName("pref")
    val preference: Int,
    @SerializedName("rst")
    val foodRestrictions: List<HealthProperties>
)

data class Health(
    @SerializedName("ail")
    val ailments: List<HealthProperties>,
    @SerializedName("med")
    val medications: List<HealthProperties>,
    @SerializedName("inj")
    val injuries: List<Injury>,
    @SerializedName("tgt")
    val targets: List<HealthProperties>
)

data class Injury(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: Int,
    @SerializedName("dur")
    val sinceWhen: Long
)

data class LifeStyle(
    @SerializedName("slp")
    val sleep: Session,
    @SerializedName("wrkHr")
    val workingHours: Session,
    @SerializedName("act")
    val physicalActivity: HealthProperties,
    @SerializedName("wrkSty")
    val workStyle: HealthProperties,
    @SerializedName("curAct")
    val curActivities: List<HealthProperties>,
    @SerializedName("prefAct")
    val prefActivities: List<HealthProperties>,
    @SerializedName("tgt")
    val targets: List<HealthProperties>
)

data class Session(
    @SerializedName("start")
    val bedTime: Double,
    @SerializedName("end")
    val wakeTime: Int
)

data class HealthProperties(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("code")
    val code: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("dsc")
    val description: String,
)