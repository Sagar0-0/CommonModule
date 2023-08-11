package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.BmiData
import fit.asta.health.navigation.track.model.net.common.Health
import fit.asta.health.navigation.track.model.net.common.Status

data class HomeMenuResponse(
    @SerializedName("data")
    val homeMenuData: HomeMenuData,
    @SerializedName("status")
    val status: Status
) {
    data class HomeMenuData(
        @SerializedName("HealthDtl")
        val healthDetail: Health,
        @SerializedName("TimeSpent")
        val timeSpent: TimeSpent,
        @SerializedName("Tools")
        val tools: List<Tool>,
        @SerializedName("bmi")
        val bmi: BmiData,
        @SerializedName("walking")
        val walking: Walking
    )
}