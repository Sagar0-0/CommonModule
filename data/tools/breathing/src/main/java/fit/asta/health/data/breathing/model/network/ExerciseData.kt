package fit.asta.health.data.breathing.model.network


import com.google.gson.annotations.SerializedName

data class ExerciseData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("lvl")
    val level: Int,
    @SerializedName("mda")
    val mda: Mda,
    @SerializedName("rto")
    val ratio: Ratio,
    @SerializedName("type")
    val type: Int
)