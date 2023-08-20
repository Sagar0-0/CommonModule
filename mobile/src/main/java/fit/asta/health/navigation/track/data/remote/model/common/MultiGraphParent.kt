package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class MultiGraphParent(
    @SerializedName("data")
    val multiGraphDataList: List<MultiGraphData>,
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>
) {

    data class MultiGraphData(
        @SerializedName("name")
        val name: String,
        @SerializedName("xVal")
        val xVal: List<String>,
        @SerializedName("yVal")
        val yVal: List<Float>
    )
}