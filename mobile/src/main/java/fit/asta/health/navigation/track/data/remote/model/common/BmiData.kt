package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class BmiData(
    @SerializedName("bmi")
    val bmi: Float,
    @SerializedName("idealBmi")
    val idealBmi: Float,
    @SerializedName("idealWgt")
    val idealWgt: Float,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("weight_unit")
    val weightUnit: String
)