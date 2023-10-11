package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class AllExerciseData(
    @SerializedName("customRatioData")
    val customRatioData: List<CustomRatioData>,
    @SerializedName("exerciseData")
    val exerciseData: List<ExerciseData>
)