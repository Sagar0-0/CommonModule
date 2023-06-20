package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName


data class NetWaterWeeklyRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetWaterWeeklyData
)

data class NetWaterWeeklyData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("month")
    val month: String,
    @SerializedName("weekly")
    val weeklyData: List<NetWeeklyData>,
    @SerializedName("amount_consumed")
    val amountConsumed: NetAmountConsumed,
    @SerializedName("daily_progress")
    val dailyProgress: NetDailyProgress,
    @SerializedName("ratio")
    val ratio: NetRatio,
    @SerializedName("beverages")
    val beverages: NetBeveragesList
)

data class NetWeeklyData(
    @SerializedName("day")
    val day: String,
    @SerializedName("percent")
    val percentage: Int
)