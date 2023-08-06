package fit.asta.health.navigation.track.model.net.water

import com.google.gson.annotations.SerializedName

data class DailyProgress(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("xData")
    val xData: List<String>,
    @SerializedName("yData")
    val yData: List<Double>
)