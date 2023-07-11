package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class AllExerciseData(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: Status
)