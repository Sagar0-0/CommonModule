package fit.asta.health.data.profile.remote.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.UiString

data class UserProfile(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("cont") val contact: Contact = Contact(),
    @SerializedName("phq") val physique: Physique = Physique(),
    @SerializedName("hlt") val health: Health = Health(),
    @SerializedName("ls") val lifeStyle: LifeStyle = LifeStyle(),
    @SerializedName("diet") val diet: Diet = Diet(),
)

data class Contact(
    @SerializedName("adr") val address: Address = Address(),
    @SerializedName("dob") val dob: String = "",
    @SerializedName("mail") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("ph") val phone: String = "",
    @SerializedName("media") val url: ProfileMedia = ProfileMedia(),
    val localUrl: Uri? = null,
)

data class ProfileMedia(
    val name: String = "",
    val title: String = "",
    @SerializedName("url") var url: String = "",
    var localUrl: Uri? = null,
    val error: UiString = UiString.Empty,
)

data class Address(
    @SerializedName("adr1") val address: String = "",
    @SerializedName("cnt") val country: String = "",
    @SerializedName("cty") val city: String = "",
    @SerializedName("pin") val pin: String = "",
    @SerializedName("st") val street: String = "",
)

data class Physique(
    @SerializedName("age") val age: Int = 0, //NOT DONE
    @SerializedName("bdt") val bodyType: Int = 0,
    @SerializedName("bmi") val bmi: Float = 0f,  //NOT DONE
    @SerializedName("gen") val gender: Int = 0, //recheck
    @SerializedName("ht") val height: Float = 0f,
    @SerializedName("prg") val isPregnant: Int = 0,
    @SerializedName("prd") val onPeriod: Int = 0,
    @SerializedName("pw") val pregnancyWeek: Int? = 0,
    @SerializedName("wt") val weight: Float = 0f,
)

data class Health(
    @SerializedName("hh") val healthHistory: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("ail") val ailments: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("med") val medications: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("inj") val injuries: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("bp")
    val bodyPart: ArrayList<HealthProperties>? = arrayListOf(),// Body Part healthHisList missing
    @SerializedName("htg") val healthTargets: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("add") val addiction: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("is") val injurySince: Int? = 0,
)

//working env missing
data class LifeStyle(
    @SerializedName("act") var physicalActivity: Int? = 0,
    @SerializedName("env") var workingEnv: Int? = 0,
    @SerializedName("ws") var workStyle: Int? = 0,
    @SerializedName("whr") var workingHours: Int? = 0,
    @SerializedName("cat") val curActivities: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("pat") val prefActivities: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("lst") val lifeStyleTargets: ArrayList<HealthProperties>? = arrayListOf(),

    @SerializedName("wt") var workingTime: Session = Session(),
    @SerializedName("slp") var sleep: Session = Session(), // missing in UI
)

data class Diet(
    @SerializedName("pref") var preference: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("nv") val nonVegDays: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("alg") val allergies: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("cns") val cuisines: ArrayList<HealthProperties>? = arrayListOf(),
    @SerializedName("frs") val foodRestrictions: ArrayList<HealthProperties>? = arrayListOf(),
)

data class Session(
    @SerializedName("str") val from: Float = 0.0f,
    @SerializedName("end") val to: Float = 0.0f,
)

data class Injury(
    @SerializedName("id") val id: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("dur") val sinceWhen: Double = 0.0,
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