package fit.asta.health.profile.model.domain


data class UserProfile(
    val uid: String,
    val contact: Contact,
    val physique: Physique,
    val lifestyle: ArrayList<ProfileItem>,
    val health: ArrayList<ProfileItem>,
    val diet: ArrayList<ProfileItem>
)

class Contact(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var phone: String = "",
    var imgUrl: String = "",
    var address: String = ""
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
    val description: String = ""
)

enum class ProfileItemType(val value: Int) {

    Contact(1),
    PlainCard(2),
    BodyTypeCard(3),
    SessionCard(4),
    ChipsCard(5);

    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

sealed class ProfileItem {

    class ChipCard(
        var id: String = "",
        var label: String = "",
        var image: String = "",
        var value: ArrayList<HealthProperties> = arrayListOf(),
        var profileCardType: ProfileItemType = ProfileItemType.ChipsCard
    ) : ProfileItem()

    class PlainCard(
        var id: String = "",
        var label: String = "",
        var image: String = "",
        var itemValue: String = "",
        var updatedValue: String = "",
        var profileCardType: ProfileItemType = ProfileItemType.PlainCard
    ) : ProfileItem()

    class SessionCard(
        var label: String = "Sleep Schedule",
        var image: Int = 0,
        var startTime: String = "",
        var endTime: String = "",
        var profileCardType: ProfileItemType = ProfileItemType.SessionCard
    ) : ProfileItem()
}