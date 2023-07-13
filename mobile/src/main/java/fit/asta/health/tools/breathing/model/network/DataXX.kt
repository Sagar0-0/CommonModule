package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class DataXX(
    @SerializedName("ExerciseList")
    val exerciseList: List<ExerciseData>,
    @SerializedName("MusicData")
    val musicData: MusicData
)