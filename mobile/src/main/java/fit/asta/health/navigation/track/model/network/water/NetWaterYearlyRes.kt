package fit.asta.health.navigation.track.model.network.water

import com.google.gson.annotations.SerializedName

data class NetWaterYearlyRes(
    @SerializedName("status")
    val status: NetStatus,
    @SerializedName("data")
    val data: NetWaterYearlyData
)

data class NetWaterYearlyData(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("amtCon")
    val amtCon: NetAmountConsumed,
    @SerializedName("dailyProgress")
    val dailyProgress: NetDailyProgress,
    @SerializedName("ratio")
    val ratio: NetRatio,
    @SerializedName("beverages")
    val beverages: NetBeveragesList
)
