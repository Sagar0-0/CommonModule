package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("customRatioData")
    val customRatioData: Any,
    @SerializedName("exerciseData")
    val exerciseData: List<ExerciseData>
)