package fit.asta.health.referral.remote.model

import com.google.gson.annotations.SerializedName

typealias UserProfileImageType = Int
data class UserDetails(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("ph")
    val phone: String = "",
    @SerializedName("prime")
    val prime: Int = 0,
    @SerializedName("type")
    val imageType: UserProfileImageType = 0,
)

enum class UserProfileImageTypes(val imageType: UserProfileImageType) {
    GOOGLE(1),
    SERVER(2)
}

enum class PrimeUserTypes(val code: Int) {
    ACTIVE(1),
    INACTIVE(2),
    TEMPORARY_INACTIVE(3)
}