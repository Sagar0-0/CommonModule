package fit.asta.health.referral.remote.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    val phone: String = "",
    @SerializedName("prime")
    val prime: Boolean = false
)