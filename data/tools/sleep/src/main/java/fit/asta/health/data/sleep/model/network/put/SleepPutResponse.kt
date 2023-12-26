package fit.asta.health.data.sleep.model.network.put

import com.google.gson.annotations.SerializedName
import fit.asta.health.data.sleep.model.network.common.Status

data class SleepPutResponse(
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: Status
)