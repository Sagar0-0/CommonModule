package fit.asta.health.tools.sleep.model.network.common

import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)