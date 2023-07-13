package fit.asta.health.tools.sleep.model.network.post

import com.google.gson.annotations.SerializedName

data class SleepPostRequestBody(
    @SerializedName("dis")
    val dis: List<String>,
    @SerializedName("dur")
    val dur: Double,
    @SerializedName("fac")
    val fac: List<String>,
    @SerializedName("goal")
    val goal: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("reg")
    val reg: Double,
    @SerializedName("slp")
    val slp: Slp,
    @SerializedName("uid")
    val uid: String
)