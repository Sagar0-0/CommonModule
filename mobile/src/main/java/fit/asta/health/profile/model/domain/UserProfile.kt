package fit.asta.health.profile.model.domain

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("cont")
    val contact: Contact,
    @SerializedName("phq")
    val physique: Physique,
    @SerializedName("hlt")
    val health: Health,
    @SerializedName("ls")
    val lifeStyle: LifeStyle,
    @SerializedName("diet")
    val diet: Diet,
)

data class Contact(
    @SerializedName("adr")
    val address: Address,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("mail")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ph")
    val phone: String,
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
    @SerializedName("bdt")
    val bodyType: Int,
    @SerializedName("bmi")
    val bmi: Int,
    @SerializedName("gen")
    val gender: String,
    @SerializedName("ht")
    val height: Int,
    @SerializedName("prg")
    val isPregnant: Boolean,
    @SerializedName("pw")
    val pregnancyWeek: Int,
    @SerializedName("wt")
    val weight: Int
)

data class Health(
    @SerializedName("ail")
    val ailments: ArrayList<HealthProperties>,
    @SerializedName("med")
    val medications: ArrayList<HealthProperties>,
    @SerializedName("inj")
    val injuries: ArrayList<Injury>,
    @SerializedName("htg")
    val targets: ArrayList<HealthProperties>
)

data class LifeStyle(
    @SerializedName("slp")
    var sleep: Session,
    @SerializedName("whr")
    var workingHours: Session,
    @SerializedName("act")
    var physicalActivity: HealthProperties,
    @SerializedName("ws")
    var workStyle: HealthProperties,
    @SerializedName("cat")
    val curActivities: ArrayList<HealthProperties>,
    @SerializedName("pat")
    val prefActivities: ArrayList<HealthProperties>,
    @SerializedName("lst")
    val targets: ArrayList<HealthProperties>
)

data class Diet(
    @SerializedName("alg")
    val allergies: ArrayList<HealthProperties>,
    @SerializedName("cns")
    val cuisines: ArrayList<HealthProperties>,
    @SerializedName("nv")
    val nonVegDays: ArrayList<String>,
    @SerializedName("pref")
    var preference: Int,
    @SerializedName("frs")
    val foodRestrictions: ArrayList<HealthProperties>
)

data class Session(
    @SerializedName("str")
    val from: Double,
    @SerializedName("end")
    val to: Double
)

data class Injury(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dur")
    val sinceWhen: Double
)

/*
data class UserProfile(
    val uid: String,
    val contact: Contact,
    val physique: Physique,
    val lifestyle: Map<UserPropertyType, ArrayList<HealthProperties>>,
    val health: Map<UserPropertyType, ArrayList<HealthProperties>>,
    val diet: Map<UserPropertyType, ArrayList<HealthProperties>>
)

class Contact(
    var id: String = "",
    var name: String = "",
    var dob: String = "",
    var email: String = "",
    var phone: String = "",
    var imgUrl: String = "",
    var address: Address
)

data class Address(
    val address: String = "",
    val street: String = "",
    val city: String = "",
    val country: String = "",
    val pin: String = "",
)

data class Physique(
    val age: Int = 0,
    val bodyType: Int = 0,
    val bmi: Int = 0,
    val gender: String = "",
    val height: Int = 0,
    val isPregnant: Boolean = false,
    val pregnancyWeek: Int = 0,
    val weight: Int = 0,
)

data class HealthProperties(
    val id: String = "",
    val type: Int = 0,
    val code: String = "",
    val name: String = "",
    val description: String = "",
    val from: Double = 0.0,
    val to: Double = 0.0,
)
*/