package fit.asta.health.data.profile.remote.model

import android.net.Uri
import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.UiString

data class UserProfileResponse(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("cont") val userDetail: UserDetail = UserDetail(),
    @SerializedName("phq") val physique: Physique = Physique(),
    @SerializedName("hlt") val health: Health = Health(),
    @SerializedName("ls") val lifeStyle: LifeStyle = LifeStyle(),
    @SerializedName("diet") val diet: Diet = Diet(),
)

data class UserDetail(
    @SerializedName("adr") val userProfileAddress: UserProfileAddress = UserProfileAddress(),
    @SerializedName("dob") val dob: String = "",
    @SerializedName("mail") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("ph") val phoneNumber: String = "",
    @SerializedName("media") val media: ProfileMedia = ProfileMedia()
)

data class ProfileMedia(
    @SerializedName("url") var url: String = "",
    @SerializedName("mailUrl") var mailUrl: String = "",
    var localUrl: Uri? = null,
    val error: UiString = UiString.Empty,
)

data class UserProfileAddress(
    @SerializedName("adr1") val address: String = "Address Line 1",
    @SerializedName("cnt") val country: String = "India",
    @SerializedName("cty") val city: String = "Noida",
    @SerializedName("pin") val pin: String = "123456",
    @SerializedName("st") val street: String = "Some Street",
) {
    override fun toString(): String {
        return "$address, $street, $city, $country"
    }
}

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
    @SerializedName("med") val medications: ArrayList<HealthProperties>? = null,
    @SerializedName("htg") val targets: ArrayList<HealthProperties>? = null,
    @SerializedName("ail") val ailments: ArrayList<HealthProperties>? = null,
    @SerializedName("hh") val healthHistory: ArrayList<HealthProperties>? = null,
    @SerializedName("inj") val injuries: ArrayList<HealthProperties>? = null,
    @SerializedName("bp") val bodyPart: ArrayList<HealthProperties>? = null,// Body Part healthHisList missing
    @SerializedName("add") val addiction: ArrayList<HealthProperties>? = null,
    @SerializedName("is") val injurySince: Int? = 0,
)

//working env missing
data class LifeStyle(
    @SerializedName("act") var physicalActivity: Int? = 0,
    @SerializedName("env") var workingEnv: Int? = 0,
    @SerializedName("ws") var workStyle: Int? = 0,
    @SerializedName("whr") var workingHours: Int? = 0,
    @SerializedName("cat") val curActivities: ArrayList<HealthProperties>? = null,
    @SerializedName("pat") val prefActivities: ArrayList<HealthProperties>? = null,
    @SerializedName("lst") val lifeStyleTargets: ArrayList<HealthProperties>? = null,

    @SerializedName("wt") var workingTime: TimeSchedule = TimeSchedule(),
    @SerializedName("slp") var sleep: TimeSchedule = TimeSchedule(), // missing in UI
)

data class Diet(
    @SerializedName("pref") var preference: ArrayList<HealthProperties>? = null,
    @SerializedName("nv") val nonVegDays: ArrayList<HealthProperties>? = null,
    @SerializedName("alg") val allergies: ArrayList<HealthProperties>? = null,
    @SerializedName("cns") val cuisines: ArrayList<HealthProperties>? = null,
    @SerializedName("frs") val restrictions: ArrayList<HealthProperties>? = null,
)

data class TimeSchedule(
    @SerializedName("str") val from: Float = 0.0f,
    @SerializedName("end") val to: Float = 0.0f,
)

data class Injury(
    @SerializedName("id") val id: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("dur") val sinceWhen: Double = 0.0,
)