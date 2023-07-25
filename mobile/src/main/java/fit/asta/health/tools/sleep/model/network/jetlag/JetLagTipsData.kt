package fit.asta.health.tools.sleep.model.network.jetlag

import com.google.gson.annotations.SerializedName

data class JetLagTipsData(
    @SerializedName("dtl")
    val jetLagTipDetails: List<JetLagTipDetails>,
    @SerializedName("guide")
    val guide: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("img")
    val img: String,
    @SerializedName("ttl")
    val ttl: String,
    @SerializedName("type")
    val type: String
)