package fit.asta.health.profile.data.chips


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("val")
    val value: String = ""
)