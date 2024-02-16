package fit.asta.health.data.profile.remote.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileResponse(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("cont") val userDetail: UserDetail = UserDetail(),
    @SerializedName("phq") val physique: Physique = Physique(),
    @SerializedName("hlt") val health: Health = Health(),
    @SerializedName("ls") val lifeStyle: LifeStyle = LifeStyle(),
    @SerializedName("diet") val diet: Diet = Diet(),
) : Parcelable

@Parcelize
data class UserDetail(
    @SerializedName("adr") val userProfileAddress: UserProfileAddress = UserProfileAddress(),
    @SerializedName("dob") val dob: String = "",
    @SerializedName("mail") val email: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("ph") val phoneNumber: String = "",
    @SerializedName("media") val media: ProfileMedia = ProfileMedia()
) : Parcelable

@Parcelize
data class ProfileMedia(
    @SerializedName("url") var url: String = "",
    @SerializedName("mailUrl") var mailUrl: String = "",
    var localUri: Uri? = null
) : Parcelable

@Parcelize
data class UserProfileAddress(
    @SerializedName("adr1") val address: String = "Address Line 1",
    @SerializedName("cnt") val country: String = "India",
    @SerializedName("cty") val city: String = "Noida",
    @SerializedName("pin") val pin: String = "123456",
    @SerializedName("st") val street: String = "Some Street",
) : Parcelable {
    override fun toString(): String {
        return "$address, $street, $city, $country"
    }
}

@Parcelize
data class Physique(
    @SerializedName("age") val age: Int = 0, //NOT DONE
    @SerializedName("bdt") val bodyType: Int = 0,
    @SerializedName("bmi") val bmi: Float = 0f,  //NOT DONE
    @SerializedName("gen") val gender: Gender = 0, //recheck
    @SerializedName("ht") val height: Float = 0f,
    @SerializedName("prg") val isPregnant: BooleanInt = 0,
    @SerializedName("prd") val onPeriod: BooleanInt = 0,
    @SerializedName("pw") val pregnancyWeek: Int? = 0,
    @SerializedName("wt") val weight: Float = 0f,
) : Parcelable

@Parcelize
data class Health(
    @SerializedName("med") val medications: List<HealthProperties>? = null,
    @SerializedName("htg") val targets: List<HealthProperties>? = null,
    @SerializedName("ail") val ailments: List<HealthProperties>? = null,
    @SerializedName("hh") val healthHistory: List<HealthProperties>? = null,
    @SerializedName("inj") val injuries: List<HealthProperties>? = null,
    @SerializedName("bp") val bodyPart: List<HealthProperties>? = null,// Body Part healthHisList missing
    @SerializedName("add") val addiction: List<HealthProperties>? = null,
    @SerializedName("is") val injurySince: Int? = 0,
) : Parcelable

//working env missing
@Parcelize
data class LifeStyle(
    @SerializedName("act") var physicalActivity: Int? = 0,
    @SerializedName("env") var workingEnv: Int? = 0,
    @SerializedName("ws") var workStyle: Int? = 0,
    @SerializedName("whr") var workingHours: Int? = 0,
    @SerializedName("cat") val curActivities: List<HealthProperties>? = null,
    @SerializedName("pat") val prefActivities: List<HealthProperties>? = null,
    @SerializedName("lst") val lifeStyleTargets: List<HealthProperties>? = null,
    @SerializedName("wt") var workingTime: TimeSchedule = TimeSchedule(),
    @SerializedName("slp") var sleep: TimeSchedule = TimeSchedule(), // missing in UI
) : Parcelable

@Parcelize
data class Diet(
    @SerializedName("pref") var preference: List<HealthProperties>? = null,
    @SerializedName("nv") val nonVegDays: List<HealthProperties>? = null,
    @SerializedName("alg") val allergies: List<HealthProperties>? = null,
    @SerializedName("cns") val cuisines: List<HealthProperties>? = null,
    @SerializedName("frs") val restrictions: List<HealthProperties>? = null,
) : Parcelable

@Parcelize
data class TimeSchedule(
    @SerializedName("str") val from: Float = 0.0f,
    @SerializedName("end") val to: Float = 0.0f,
) : Parcelable

typealias Gender = Int

enum class GenderTypes(val gender: Gender, val title: String) {
    MALE(1, "Male"),
    FEMALE(2, "Female"),
    OTHER(3, "Other")
}

typealias BooleanInt = Int

enum class BooleanIntTypes(val value: BooleanInt, val title: String) {
    YES(1, "Yes"),
    NO(0, "No")
}