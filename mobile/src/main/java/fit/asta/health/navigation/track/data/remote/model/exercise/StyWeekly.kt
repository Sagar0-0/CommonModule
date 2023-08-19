package fit.asta.health.navigation.track.data.remote.model.exercise

import com.google.gson.annotations.SerializedName

data class StyWeekly(
    @SerializedName("Cardio")
    val cardio: Int,
    @SerializedName("Jump rope")
    val jumpRope: Int,
    @SerializedName("Strength Training")
    val strengthTraining: Int,
    @SerializedName("Weight-Lifting")
    val weightLifting: Int
)