package fit.asta.health.navigation.track.data.remote.model.sunlight

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Weather
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class SunlightResponse(
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