package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetStatus(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)

