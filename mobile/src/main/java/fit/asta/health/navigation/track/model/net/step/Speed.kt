package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class Speed(
    @SerializedName("avgCal")
    val avgCal: Int,
    @SerializedName("avgCalUnit")
    val avgCalUnit: String,
    @SerializedName("avgInt")
    val avgInt: Int,
    @SerializedName("avgIntUnit")
    val avgIntUnit: String,
    @SerializedName("avgSpeed")
    val avgSpeed: Int,
    @SerializedName("avgSpeedUnit")
    val avgSpeedUnit: String
)