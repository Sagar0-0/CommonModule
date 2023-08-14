package fit.asta.health.navigation.track.model.net.sunlight

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.Graph
import fit.asta.health.navigation.track.model.net.common.Progress
import fit.asta.health.navigation.track.model.net.common.Status
import fit.asta.health.navigation.track.model.net.common.Weather
import fit.asta.health.navigation.track.model.net.common.Weekly

data class SunlightResponse(
    @SerializedName("data")
    val sunlightData: SunlightData,
    @SerializedName("status")
    val status: Status
) {

    data class SunlightData(
        @SerializedName("durGph")
        val durationGraph: Graph?,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("expGph")
        val exposureGraph: Graph?,
        @SerializedName("id")
        val id: String,
        @SerializedName("mid")
        val mid: List<String>,
        @SerializedName("month")
        val month: String?,
        @SerializedName("moodGph")
        val moodGraph: Graph?,
        @SerializedName("progress")
        val progress: Progress?,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("vitGph")
        val vitaminGraph: Graph?,
        @SerializedName("weatherDtl")
        val weather: Weather?,
        @SerializedName("weekly")
        val weekly: List<Weekly>?,
        @SerializedName("year")
        val year: String
    )
}