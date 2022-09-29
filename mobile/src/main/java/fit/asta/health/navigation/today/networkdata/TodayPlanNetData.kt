package fit.asta.health.navigation.today.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class TodayPlanNetData(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<TodayPlanItemNetData> = listOf()
)
