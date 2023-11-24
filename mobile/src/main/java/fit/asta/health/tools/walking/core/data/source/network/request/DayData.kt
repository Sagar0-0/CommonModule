package fit.asta.health.tools.walking.core.data.source.network.request


import com.google.gson.annotations.SerializedName

data class DayData(
    @SerializedName("act")
    val activity: Activity,
    @SerializedName("bpm")
    val bpm: Double,
    @SerializedName("cal")
    val calories: Int,
    @SerializedName("exp")
    val exposure: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("int")
    val intensity: Int,
    @SerializedName("mode")
    val mode: String,
    @SerializedName("pace")
    val pace: String,
    @SerializedName("speed")
    val speed: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wl")
    val weightLoose: Double
)
data class Activity(
    @SerializedName("dis")
    val distance: Distance,
    @SerializedName("dur")
    val duration: Duration,
    @SerializedName("steps")
    val steps: Steps
)