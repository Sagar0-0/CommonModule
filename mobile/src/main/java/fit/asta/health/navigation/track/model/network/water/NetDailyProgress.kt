package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetDailyProgress(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("xData")
    val xData: List<String>,
    @SerializedName("yData")
    val yData: List<Double>
)
