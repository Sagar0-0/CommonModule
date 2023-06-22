package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetBeverageData(
    @SerializedName("name")
    val name: String,
    @SerializedName("xVal")
    val xVal: List<String>?,
    @SerializedName("yVal")
    val yVal: List<Double>?
)