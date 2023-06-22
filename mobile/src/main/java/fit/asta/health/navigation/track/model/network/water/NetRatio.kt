package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetRatio(
    @SerializedName("water")
    val waterRatio: Double,
    @SerializedName("juice")
    val juiceRatio: Double,
    @SerializedName("drink")
    val drinkRatio: Double
)