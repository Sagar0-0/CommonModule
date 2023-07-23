package fit.asta.health.tools.sleep.model.network.goals

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Status

data class SleepGoalResponse(
    @SerializedName("data")
    val data: List<SleepGoalData>,
    @SerializedName("status")
    val status: Status
)