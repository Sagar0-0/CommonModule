package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class NetGetStart(
    @SerializedName("ExerciseList")
    val exerciseList: List<ExerciseData>,
    @SerializedName("MusicData")
    val musicData: MusicData
)