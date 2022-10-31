package fit.asta.health.profile.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetUserProfileRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val userProfile: NetUserProfile
)

data class NetUserProfile(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("cont")
    val contact: NetContact,
    @SerializedName("phq")
    val physique: NetPhysique,
    @SerializedName("hlt")
    val health: NetHealth,
    @SerializedName("ls")
    val lifeStyle: NetLifeStyle,
    @SerializedName("diet")
    val diet: NetDiet,
)

data class NetContact(
    @SerializedName("adr")
    val address: NetAddress,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("mail")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ph")
    val ph: String,
    @SerializedName("url")
    val url: String
)

data class NetAddress(
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

data class NetPhysique(
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

data class NetHealth(
    @SerializedName("ail")
    val ailments: ArrayList<NetHealthProperties>,
    @SerializedName("med")
    val medications: ArrayList<NetHealthProperties>,
    @SerializedName("inj")
    val injuries: ArrayList<NetInjury>,
    @SerializedName("htg")
    val targets: ArrayList<NetHealthProperties>
)

data class NetLifeStyle(
    @SerializedName("slp")
    var sleep: NetSession,
    @SerializedName("whr")
    var workingHours: NetSession,
    @SerializedName("act")
    var physicalActivity: NetHealthProperties,
    @SerializedName("ws")
    var workStyle: NetHealthProperties,
    @SerializedName("cat")
    val curActivities: ArrayList<NetHealthProperties>,
    @SerializedName("pat")
    val prefActivities: ArrayList<NetHealthProperties>,
    @SerializedName("lst")
    val targets: ArrayList<NetHealthProperties>
)

data class NetDiet(
    @SerializedName("alg")
    val allergies: ArrayList<NetHealthProperties>,
    @SerializedName("cns")
    val cuisines: ArrayList<NetHealthProperties>,
    @SerializedName("nv")
    val nonVegDays: ArrayList<String>,
    @SerializedName("pref")
    var preference: Int,
    @SerializedName("frs")
    val foodRestrictions: ArrayList<NetHealthProperties>
)

data class NetSession(
    @SerializedName("str")
    val from: Double,
    @SerializedName("end")
    val to: Double
)

data class NetInjury(
    @SerializedName("id")
    val id: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dur")
    val sinceWhen: Double
)

data class NetHealthProperties(
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("dsc")
    val description: String
)