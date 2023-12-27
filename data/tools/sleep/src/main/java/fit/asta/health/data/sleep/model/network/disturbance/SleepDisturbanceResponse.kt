package fit.asta.health.data.sleep.model.network.disturbance

import com.google.gson.annotations.SerializedName

data class SleepDisturbanceResponse(
    @SerializedName("sleepData")
    val sleepData: SleepData,
)