package fit.asta.health.data.profile.remote.model

import com.google.gson.annotations.SerializedName

data class BasicProfileDTO(
    @SerializedName("uid") val uid: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("gen") val gen: String = "",
    @SerializedName("mail") val mail: String? = null,
    @SerializedName("ph") val ph: String? = null,
    @SerializedName("refCode") val refCode: String = "",
)
