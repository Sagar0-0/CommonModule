package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetBeveragesData(
    @SerializedName("unit")
    val unit: Int,
    @SerializedName("xAxis")
    val xAxis: List<String>,
    @SerializedName("data")
    val data: List<NetBeverageList>
)
