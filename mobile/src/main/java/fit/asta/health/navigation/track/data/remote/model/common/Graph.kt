package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class Graph(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("yData")
    val yData: List<Float>
)