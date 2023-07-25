package fit.asta.health.tools.sleep.model.network.get

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.ToolData

data class SleepData(
    @SerializedName("progressData")
    val progressData: ProgressData,
    @SerializedName("toolData")
    val toolData: ToolData
)