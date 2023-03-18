package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetWalkingToolRes(
    @SerializedName("status")
    val status: Status,
    @SerializedName("data")
    val data: NetWalkingTool
)

