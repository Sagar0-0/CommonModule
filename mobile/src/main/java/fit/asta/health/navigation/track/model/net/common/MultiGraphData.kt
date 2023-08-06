package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName

data class MultiGraphData(
    @SerializedName("name")
    val name: String,
    @SerializedName("xVal")
    val xVal: List<String>,
    @SerializedName("yVal")
    val yVal: List<Double>
)