package fit.asta.health.data.sleep.model.network.get

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.sleep.model.network.common.Status

data class SleepToolGetResponse(
    @SerializedName("sleepData")
    val sleepData: SleepData,
    @SerializedName("status")
    val status: Status
)