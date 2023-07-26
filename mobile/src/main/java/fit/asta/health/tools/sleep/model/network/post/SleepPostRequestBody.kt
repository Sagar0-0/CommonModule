package fit.asta.health.tools.sleep.model.network.post

import com.google.gson.annotations.SerializedName

data class SleepPostRequestBody(
    @SerializedName("dur")
    val dur: Double,
    @SerializedName("id")
    val id: String,
    @SerializedName("reg")
    val reg: Double,
    @SerializedName("slp")
    val slp: Slp,
    @SerializedName("uid")
    val uid: String
)