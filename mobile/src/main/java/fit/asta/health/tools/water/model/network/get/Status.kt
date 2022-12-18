package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("code")
    val code: Int,
    @SerializedName("msg")
    val msg: String
)