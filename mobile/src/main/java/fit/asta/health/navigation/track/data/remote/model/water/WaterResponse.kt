package fit.asta.health.navigation.track.data.remote.model.water

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.MultiGraphParent
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Status
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class WaterResponse(
    @SerializedName("data")
    val waterData: WaterData,
    @SerializedName("status")
    val status: Status
) {

    data class WaterData(
        @SerializedName("amount_consumed")
        val amountConsumed: AmountConsumed?,
        @SerializedName("beverages")
        val beverageData: MultiGraphParent?,
        @SerializedName("dailyProgress")
        val progress: Graph?,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("id")
        val id: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("progress")
        val dailyProgress: Progress?,
        @SerializedName("ratio")
        val ratio: Ratio?,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("weather")
        val weather: Weather?,
        @SerializedName("weekly")
        val weekly: List<Weekly>?,
        @SerializedName("year")
        val year: String
    )
}