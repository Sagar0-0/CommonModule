package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName


data class NetWaterMonthlyRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetWaterMonthlyData
)

data class NetWaterMonthlyData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("month")
    val month: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("amtCon")
    val amountConsumed: NetAmountConsumed,
    @SerializedName("dailyProgress")
    val dailyProgress: NetDailyProgress,
    @SerializedName("ratio")
    val ratio: NetRatio,
    @SerializedName("beverages")
    val beverages: NetBeveragesList
)
