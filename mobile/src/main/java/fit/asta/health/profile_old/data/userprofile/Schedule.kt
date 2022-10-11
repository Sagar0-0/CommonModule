package fit.asta.health.profile_old.data.userprofile


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("bedTime")
    var bedTime: String = "",
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("wakeUp")
    var wakeUp: String = ""
)