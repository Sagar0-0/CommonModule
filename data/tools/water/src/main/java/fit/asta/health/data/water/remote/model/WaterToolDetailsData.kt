package fit.asta.health.data.water.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.water.local.entity.Status

data class WaterToolDetailsData(
    @SerializedName("data")
    val `data`: List<WaterDetailsData>,
    @SerializedName("status")
    val status: Status
)