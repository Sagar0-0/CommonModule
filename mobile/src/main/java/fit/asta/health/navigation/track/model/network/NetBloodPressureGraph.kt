package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetBloodPressureGraph(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("data")
    val data: List<NetBloodPressureData>
)


data class NetBloodPressureData(
    @SerializedName("name")
    val name: String,
    @SerializedName("xVal")
    val xVal: List<String>,
    @SerializedName("yVal")
    val yVal: List<Int>
)