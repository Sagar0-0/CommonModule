package fit.asta.health.navigation.home.network.model

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)
