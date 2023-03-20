package fit.asta.health.network.data


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("msg") val msg: String = "",
)