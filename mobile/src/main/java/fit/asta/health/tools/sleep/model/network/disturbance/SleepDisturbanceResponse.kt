package fit.asta.health.tools.sleep.model.network.disturbance

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Status

data class SleepDisturbanceResponse(
    @SerializedName("sleepData")
    val sleepData: SleepData,
    @SerializedName("status")
    val status: Status
)