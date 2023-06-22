package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetAmountConsumed(
    @SerializedName("dailyAvg")
    val dailyAverage: Double,
    @SerializedName("totalAmt")
    val totalAmount: Int
)
