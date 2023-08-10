package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName

data class TimeSpent(
    @SerializedName("breathing")
    val breathing: Float,
    @SerializedName("meditation")
    val meditation: Float,
    @SerializedName("sleep")
    val sleep: Float,
    @SerializedName("steps")
    val steps: Float,
    @SerializedName("sunlight")
    val sunlight: Float,
    @SerializedName("water")
    val water: Float
)