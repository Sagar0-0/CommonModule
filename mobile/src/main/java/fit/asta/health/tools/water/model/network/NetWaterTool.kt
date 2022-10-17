package fit.asta.health.tools.water.model.network

import com.google.gson.annotations.SerializedName

data class NetWaterTool(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
)
