package fit.asta.health.tools.sleep.model.network.jetlag

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Status

data class SleepJetLagTipResponse(
    @SerializedName("data")
    val jetLagTipsData: JetLagTipsData,
    @SerializedName("status")
    val status: Status
)