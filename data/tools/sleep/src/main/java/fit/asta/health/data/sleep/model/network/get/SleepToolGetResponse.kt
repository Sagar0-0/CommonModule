package fit.asta.health.data.sleep.model.network.get

import com.google.gson.annotations.SerializedName

data class SleepToolGetResponse(
    @SerializedName("sleepData")
    val sleepData: SleepData
)