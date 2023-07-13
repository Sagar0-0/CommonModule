package fit.asta.health.tools.sleep.model.network.get

import com.google.gson.annotations.SerializedName

data class SleepData(
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("toolData")
    val toolData: ToolData
)