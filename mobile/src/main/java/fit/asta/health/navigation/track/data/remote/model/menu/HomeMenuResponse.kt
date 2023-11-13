package fit.asta.health.navigation.track.data.remote.model.menu

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.BmiData
import fit.asta.health.navigation.track.data.remote.model.common.Health

data class HomeMenuResponse(
    @SerializedName("HealthDtl")
    val healthDetail: Health?,
    @SerializedName("TimeSpent")
    val timeSpent: TimeSpent?,
    @SerializedName("Tools")
    val tools: List<Tool>?,
    @SerializedName("bmi")
    val bmi: BmiData?,
    @SerializedName("walking")
    val walking: Walking?
)