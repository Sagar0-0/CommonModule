package fit.asta.health.old_profile.data.chips


import com.google.gson.annotations.SerializedName

data class Value(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("val")
    val value: String = ""
)