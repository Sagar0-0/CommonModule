package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("ProgressData")
    val progressData: ProgressData,
    @SerializedName("toolData")
    val toolData: ToolData
)