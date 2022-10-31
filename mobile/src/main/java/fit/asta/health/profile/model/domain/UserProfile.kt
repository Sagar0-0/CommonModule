package fit.asta.health.profile.model.domain


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