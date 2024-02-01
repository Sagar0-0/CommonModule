package fit.asta.health.data.exercise.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class NetGetRes(
    @SerializedName("data")
    val `data`: ExerciseData,
    @SerializedName("status")
    val status: Status
)