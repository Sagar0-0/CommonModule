package fit.asta.health.tools.walking.core.data.source.network.request


import com.google.gson.annotations.SerializedName

data class PutDayData(
    @SerializedName("bpm")
    val bpm: Float,
    @SerializedName("cal")
    val calories: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("dis")
    val distance: Distance,
    @SerializedName("dur")
    val duration: Duration,
    @SerializedName("hr")
    val heartRate: Float,
    @SerializedName("id")
    val id: String,
    @SerializedName("steps")
    val steps: Steps,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wl")
    val weightLoose: Float
)