package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class CheckReferralDTO(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("ph")
    val phone: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("prime")
    val prime: Int = 0
)

enum class PrimeTypes(val code: Int) {
    NOT_PRIME(0),
    PRIME(1)
}