package fit.asta.health.navigation.track.model.net.water

import com.google.gson.annotations.SerializedName

data class Ratio(
    @SerializedName("drink")
    val drink: Double,
    @SerializedName("juice")
    val juice: Double,
    @SerializedName("water")
    val water: Double
)