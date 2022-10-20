package fit.asta.health.tools.sunlight.model.network

import com.google.gson.annotations.SerializedName

data class NetSunlightTool(
    @SerializedName("dsc")
    val desc: String,
    @SerializedName("id")
    val id: String,
)
