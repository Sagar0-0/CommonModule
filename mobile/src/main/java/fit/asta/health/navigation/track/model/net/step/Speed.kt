package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class Speed(
    @SerializedName("avgCal")
    val avgCalories: Int,
    @SerializedName("avgCalUnit")
    val avgCaloriesUnit: String,
    @SerializedName("avgInt")
    val avgIntensity: Int,
    @SerializedName("avgIntUnit")
    val avgIntensityUnit: String,
    @SerializedName("avgSpeed")
    val avgSpeed: Int,
    @SerializedName("avgSpeedUnit")
    val avgSpeedUnit: String
)