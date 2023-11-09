package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class CheckReferralDTO(
    @SerializedName("mail")
    val mail: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("pic")
    val pic: String = "",
    @SerializedName("prime")
    val prime: Boolean = false
)