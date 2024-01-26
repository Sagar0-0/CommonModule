package fit.asta.health.referral.remote.model

import com.google.gson.annotations.SerializedName

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
)

enum class PrimeUserTypes(val code: Int) {
    ACTIVE(1),
    INACTIVE(2),
    TEMPORARY_INACTIVE(3)
}