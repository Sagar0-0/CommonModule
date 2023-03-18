package fit.asta.health.tools.walking.model.network.response

import com.google.gson.annotations.SerializedName

data class NetWalkingTool(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
)
