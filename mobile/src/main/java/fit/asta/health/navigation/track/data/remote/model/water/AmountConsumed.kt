package fit.asta.health.navigation.track.data.remote.model.water

import com.google.gson.annotations.SerializedName

data class AmountConsumed(
    @SerializedName("dailyAvg")
    val dailyAvg: Float,
    @SerializedName("totalAmt")
    val totalAmt: Float
)