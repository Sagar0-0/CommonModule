package fit.asta.health.navigation.track.data.remote.model.water

import com.google.gson.annotations.SerializedName

data class Ratio(
    @SerializedName("drink")
    val drink: Float,
    @SerializedName("juice")
    val juice: Float,
    @SerializedName("water")
    val water: Float
)