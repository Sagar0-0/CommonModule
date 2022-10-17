package fit.asta.health.tools.sunlight.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import fit.asta.health.tools.sunlight.model.network.NetSunlightTool

data class NetSunlightToolRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetSunlightTool
)

