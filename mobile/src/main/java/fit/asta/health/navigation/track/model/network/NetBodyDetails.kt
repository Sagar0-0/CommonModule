package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetBodyDetails(
    @SerializedName("in")
    val waterIntake: NetWaterIntake,
    @SerializedName("breath")
    val breath: NetBreath,
    @SerializedName("cal")
    val calories: NetCalories
)

data class NetWaterIntake(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetBreath(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)

data class NetCalories(
    @SerializedName("avg")
    val average: Int,
    @SerializedName("unit")
    val unit: String
)