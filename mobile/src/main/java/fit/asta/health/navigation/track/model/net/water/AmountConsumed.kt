package fit.asta.health.navigation.track.model.net.water

import com.google.gson.annotations.SerializedName

data class AmountConsumed(
    @SerializedName("dailyAvg")
    val dailyAvg: Float,
    @SerializedName("totalAmt")
    val totalAmt: Float
)