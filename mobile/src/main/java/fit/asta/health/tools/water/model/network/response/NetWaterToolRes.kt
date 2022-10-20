package fit.asta.health.tools.water.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status
import fit.asta.health.tools.water.model.network.NetWaterTool

data class NetWaterToolRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetWaterTool
)

