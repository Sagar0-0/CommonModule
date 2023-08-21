package fit.asta.health.network.data

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("msg")
    val msg: String
)