package fit.asta.health.data.sleep.model.network.jetlag

import com.google.gson.annotations.SerializedName

data class JetLagTipDetails(
    @SerializedName("dsc")
    val dsc: String,
    @SerializedName("sub")
    val sub: String
)