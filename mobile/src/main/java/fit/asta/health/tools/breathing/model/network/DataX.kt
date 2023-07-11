package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class DataX(
    @SerializedName("breathingProgressData")
    val breathingProgressData: BreathingProgressData,
    @SerializedName("breathingToolData")
    val breathingToolData: BreathingToolData
)