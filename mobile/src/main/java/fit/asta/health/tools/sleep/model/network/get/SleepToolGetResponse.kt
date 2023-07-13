package fit.asta.health.tools.sleep.model.network.get

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Status

data class SleepToolGetResponse(
    @SerializedName("sleepData")
    val sleepData: SleepData,
    @SerializedName("status")
    val status: Status
)