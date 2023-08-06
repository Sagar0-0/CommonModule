package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class Health(
    @SerializedName("bp")
    val bloodPressure: BloodPressure,
    @SerializedName("hr")
    val heartRate: HeartRate
)