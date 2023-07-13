package fit.asta.health.tools.sleep.model.network.put

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("flag")
    val flag: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("msg")
    val msg: String
)