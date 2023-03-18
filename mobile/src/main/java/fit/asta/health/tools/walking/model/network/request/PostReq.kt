package fit.asta.health.tools.walking.model.network.request

import com.google.gson.annotations.SerializedName

data class PostReq(
    @SerializedName("act")
    val activity: Activity,
    @SerializedName("bpm")
    val bloodPressure: Double,
    @SerializedName("cal")
    val calories: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("pace")
    val pace: String,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wl")
    val wl: Double
)
data class Activity(
    @SerializedName("dis")
    val distance: Double,
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("steps")
    val steps: Int
)