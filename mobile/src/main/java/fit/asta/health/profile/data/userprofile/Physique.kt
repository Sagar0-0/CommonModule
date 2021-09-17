package fit.asta.health.profile.data.userprofile


import com.google.gson.annotations.SerializedName

data class Physique(
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("unit")
    var unit: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("value")
    var value: String = ""
)