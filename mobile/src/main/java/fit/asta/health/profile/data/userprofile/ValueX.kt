package fit.asta.health.profile.data.userprofile


import com.google.gson.annotations.SerializedName

data class ValueX(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("value")
    var value: String = ""
)