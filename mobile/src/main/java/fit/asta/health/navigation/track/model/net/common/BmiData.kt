package fit.asta.health.navigation.track.model.net.common

import com.google.gson.annotations.SerializedName

data class BmiData(
    @SerializedName("bmi")
    val bmi: Int,
    @SerializedName("idealBmi")
    val idealBmi: Double,
    @SerializedName("idealWgt")
    val idealWgt: Double,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("weight_unit")
    val weightUnit: String
)