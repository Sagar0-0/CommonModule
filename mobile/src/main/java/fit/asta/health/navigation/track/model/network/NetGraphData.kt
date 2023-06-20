package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetGraphData(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>?,
    @SerializedName("xData")
    val xData: List<String>?,
    @SerializedName("yData")
    val yData: List<String>?
)