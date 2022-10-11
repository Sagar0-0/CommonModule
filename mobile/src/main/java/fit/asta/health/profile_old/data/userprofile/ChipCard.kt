package fit.asta.health.profile_old.data.userprofile


import com.google.gson.annotations.SerializedName

data class ChipCard(
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("value")
    var value: ArrayList<Value> = arrayListOf()
)