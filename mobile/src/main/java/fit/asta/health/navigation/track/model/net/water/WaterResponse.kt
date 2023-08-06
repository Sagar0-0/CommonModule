package fit.asta.health.navigation.track.model.net.water

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.Status

data class WaterResponse(
    @SerializedName("data")
    val waterData: WaterData,
    @SerializedName("status")
    val status: Status
)