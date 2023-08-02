package fit.asta.health.payments.referral.model

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("prime")
    val prime: Boolean = false
)