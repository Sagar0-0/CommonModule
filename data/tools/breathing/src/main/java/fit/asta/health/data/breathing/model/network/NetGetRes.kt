package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class NetGetRes(
    @SerializedName("breathingProgressData")
    val breathingProgressData: BreathingProgressData,
    @SerializedName("breathingToolData")
    val breathingToolData: BreathingToolData
)