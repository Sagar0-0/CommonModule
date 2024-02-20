package fit.asta.health.data.water.model.network

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.water.db.dbmodel.Status

data class WaterToolDetailsData(
    @SerializedName("data")
    val `data`: List<WaterDetailsData>,
    @SerializedName("status")
    val status: Status
)