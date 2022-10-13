package fit.asta.health.profile.model.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*data class UserProfile(
    val uid: String,
    val profileData: Map<String, Any>,
    val physic: Map<String, Any>,
    val health: Map<String, Any>,
    val lifestyle: Map<String, Any>,
    val diet: Map<String, Any>
)

data class MainProfile(
    val profileUrl: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val dateOfBirth: String,
    val address: String
)

fun networkToString(s: String): String {
    return when (s) {
        "age" -> "age"
        "ht" -> "height"
        "wt" -> "weight"
        "bmi" -> "BMI"
        "prg" -> "pregnancy"
        "prgWk" -> "Pregnancy Week"
        "bdyType" -> "Body Type"
        else -> "gender"
    }
}*/

data class UserProfile(
    val uid: String,
    val contact: ProfileItem,
    val physique: ArrayList<ProfileItem>,
    val lifestyle: ArrayList<ProfileItem>,
    val health: ArrayList<ProfileItem>,
    val diet: ArrayList<ProfileItem>
)

enum class ProfileItemType(val value: Int) {

    Contact(0),
    PlainCard(1),
    BodyTypeCard(2),
    SleepScheduleCard(3),
    ChipsCard(4);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

enum class ProfileTabType(val value: Int) {

    NONE(1),
    PhysiqueTab(2),
    LifeStyleTab(3),
    HealthTargetsTab(4);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

@Parcelize
data class Value(
    var uid: String = "",
    var value: String = ""
) : Parcelable

interface ProfileItem {
    var profileType: ProfileItemType
}

class ContactItem(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var imgUrl: String = "",
    var address: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.Contact
) : ProfileItem

class BodyTypeItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var bodyTypeValue: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.BodyTypeCard
) : ProfileItem

class ChipCardItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var value: ArrayList<Value> = arrayListOf(),
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.ChipsCard
) : ProfileItem

class PlainCardItem(
    var id: String = "",
    var label: String = "",
    var image: String = "",
    var itemValue: String = "",
    var updatedValue: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.PlainCard
) : ProfileItem

class SleepScheduleItem(
    var label: String = "Sleep Schedule",
    var image: Int = 0,
    var bedTime: String = "",
    var wakeUpTime: String = "",
    var profileTabType: ProfileTabType = ProfileTabType.NONE,
    override var profileType: ProfileItemType = ProfileItemType.SleepScheduleCard
) : ProfileItem