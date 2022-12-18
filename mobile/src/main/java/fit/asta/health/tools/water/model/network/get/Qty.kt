package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class Qty(
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("value")
    val value: String
)