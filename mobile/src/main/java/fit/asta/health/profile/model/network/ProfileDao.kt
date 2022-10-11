package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class ProfileDao(
    val status: Status,
    val `data`: Map<String, Any>
)

data class UserProfileRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val `data`: Data
)

data class Data(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("cont")
    val contact: Contact,
    @SerializedName("diet")
    val diet: Diet,
    @SerializedName("hlt")
    val health: Health,
    @SerializedName("ls")
    val lifeStyle: LifeStyle,
    @SerializedName("phq")
    val physique: Physique
)

data class Contact(
    @SerializedName("addr")
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

data class Diet(
    @SerializedName("alg")
    val allergies: List<String>,
    @SerializedName("cns")
    val cns: List<String>,
    @SerializedName("nVeg")
    val nonVeg: String,
    @SerializedName("pref")
    val preference: Int,
    @SerializedName("rst")
    val rst: List<String>
)

data class Health(
    @SerializedName("hist")
    val history: History,
    @SerializedName("inj")
    val injuries: Injuries,
    @SerializedName("slp")
    val sleep: Sleep,
    @SerializedName("tgt")
    val targets: List<String>
)

data class LifeStyle(
    @SerializedName("act")
    val act: Boolean,
    @SerializedName("actInt")
    val actInt: String,
    @SerializedName("curAct")
    val curActivities: List<String>,
    @SerializedName("prefAct")
    val prefActivities: List<String>,
    @SerializedName("wrkHr")
    val workHours: Int,
    @SerializedName("wrkSty")
    val workStyle: String
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

data class Address(
    @SerializedName("addr1")
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

data class History(
    @SerializedName("ail")
    val ailments: List<String>,
    @SerializedName("code")
    val code: Int
)

data class Injuries(
    @SerializedName("code")
    val code: Int,
    @SerializedName("dtls")
    val details: List<String>,
    @SerializedName("dur")
    val duration: Int
)

data class Sleep(
    @SerializedName("bed")
    val bedTime: Double,
    @SerializedName("wake")
    val wakeTime: Int
)