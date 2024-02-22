package fit.asta.health.data.profile.remote.model

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.profile.local.entity.ProfileEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfileResponse(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("bscDtl") val basicDetail: BasicDetail = BasicDetail(),
    @SerializedName("phq") val physique: Physique = Physique(),
    @SerializedName("hlt") val health: Health = Health(),
    @SerializedName("ls") val lifeStyle: LifeStyle = LifeStyle(),
    @SerializedName("diet") val diet: Diet = Diet(),
) : Parcelable

fun UserProfileResponse.mergeWithLocalData(
    profileEntity: ProfileEntity?
): UserProfileResponse {
    if (profileEntity == null) return this
    this.basicDetail.name = profileEntity.name
    return this
}

@Parcelize
data class BasicDetail(
    @SerializedName("adr") val userProfileAddress: UserProfileAddress = UserProfileAddress(),
    @SerializedName("dob") val dob: String = "",
    @SerializedName("mail") val email: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("ph") val phoneNumber: String = "",
    @SerializedName("media") val media: ProfileMedia = ProfileMedia(),
    @SerializedName("age") val age: Int? = 0,
    @SerializedName("gen") val gender: Gender? = 0,
    @SerializedName("prg") val isPregnant: BooleanInt? = 0,
    @SerializedName("prd") val onPeriod: BooleanInt? = 0,
    @SerializedName("pw") val pregnancyWeek: Int? = 0,
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
    @SerializedName("bdt") val bodyType: Int? = 0,
    @SerializedName("bmi") val bmi: Float? = 0f,
    @SerializedName("bmiUnit") val bmiUnit: Int? = 0,
    @SerializedName("ht") val height: Float? = 0f,
    @SerializedName("htUnit") val heightUnit: Int? = 0,
    @SerializedName("wt") val weight: Float? = 0f,
    @SerializedName("wtUnit") val weightUnit: Int? = 0,
) : Parcelable

@Parcelize
data class Health(
    @SerializedName("med") val medications: List<UserProperties>? = null,
    @SerializedName("htg") val targets: List<UserProperties>? = null,
    @SerializedName("ail") val ailments: List<UserProperties>? = null,
    @SerializedName("hh") val healthHistory: List<UserProperties>? = null,
    @SerializedName("inj") val injuries: List<UserProperties>? = null,
    @SerializedName("bp") val bodyPart: List<UserProperties>? = null,// Body Part healthHisList missing
    @SerializedName("add") val addiction: List<UserProperties>? = null,
    @SerializedName("is") val injurySince: Int? = 0,
) : Parcelable

//working env missing
@Parcelize
data class LifeStyle(
    @SerializedName("act") var physicalActive: Int? = 0,
    @SerializedName("env") var workingEnv: Int? = 0,
    @SerializedName("ws") var workStyle: Int? = 0,
    @SerializedName("whr") var workingHours: Int? = 0,
    @SerializedName("cat") val curActivities: List<UserProperties>? = null,
    @SerializedName("pat") val prefActivities: List<UserProperties>? = null,
    @SerializedName("lst") val lifeStyleTargets: List<UserProperties>? = null,
    @SerializedName("wt") var workingTime: TimeSchedule = TimeSchedule(),
    @SerializedName("slp") var sleep: TimeSchedule = TimeSchedule(), // missing in UI
) : Parcelable

@Parcelize
data class Diet(
    @SerializedName("pref") var preference: List<UserProperties>? = null,
    @SerializedName("nv") val nonVegDays: List<UserProperties>? = null,
    @SerializedName("alg") val allergies: List<UserProperties>? = null,
    @SerializedName("cns") val cuisines: List<UserProperties>? = null,
    @SerializedName("frs") val restrictions: List<UserProperties>? = null,
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

fun Gender?.isMale(): Boolean {
    if (this == null) return false
    return this == GenderTypes.MALE.gender
}

fun Gender?.isFemale(): Boolean {
    if (this == null) return false
    return this == GenderTypes.FEMALE.gender
}

fun Gender?.isOther(): Boolean {
    if (this == null) return false
    return this == GenderTypes.OTHER.gender
}

fun Gender?.getGenderName(): String {
    if (this == null) return ""
    GenderTypes.entries.forEach {
        if (it.gender == this) return it.title
    }
    return ""
}

typealias BooleanInt = Int

enum class BooleanIntTypes(val value: BooleanInt, val title: String) {
    YES(1, "Yes"),
    NO(0, "No")
}

fun BooleanInt?.isTrue(): Boolean {
    if (this == null) return false
    return this == BooleanIntTypes.YES.value
}

enum class HeightUnit(val title: String, val value: Int) {
    CM("cm", 1),
    Inch("in", 6);

    companion object {
        fun indexOf(newValue: Int?): Int {
            if (newValue == null) return -1
            HeightUnit.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return -1
        }
    }

}

enum class WeightUnit(val title: String, val value: Int) {
    KG("kg", 7),
    LB("lb", 8);

    companion object {
        fun indexOf(newValue: Int?): Int {
            if (newValue == null) return -1
            WeightUnit.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return -1
        }
    }
}

enum class PhysicallyActive(val title: String, val value: Int) {
    LOW("Low", 1),
    Moderate("Moderate", 2),
    VeryActive("Very Active", 3);

    companion object {
        fun indexOf(newValue: Int): Int {
            PhysicallyActive.entries.forEachIndexed { index, unit ->
                if (unit.value == newValue) return index
            }
            return -1
        }
    }
}

enum class BMIUnit(val value: Int) {
    BMI(19)
}