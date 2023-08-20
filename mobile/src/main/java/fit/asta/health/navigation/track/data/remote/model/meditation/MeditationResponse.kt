package fit.asta.health.navigation.track.data.remote.model.meditation

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Status
import fit.asta.health.navigation.track.data.remote.model.common.Health
import fit.asta.health.navigation.track.data.remote.model.common.MultiGraphParent
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Weekly
import fit.asta.health.navigation.track.data.remote.model.common.Weather

data class MeditationResponse(
    @SerializedName("data")
    val meditationData: MeditationData,
    @SerializedName("status")
    val status: Status
) {

    data class MeditationData(
        @SerializedName("bpGph")
        val bloodPressureGraph: MultiGraphParent?,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("healthDtl")
        val healthDetail: Health?,
        @SerializedName("hrtRateGph")
        val heartRateGraph: Graph?,
        @SerializedName("id")
        val id: String,
        @SerializedName("mid")
        val mid: List<String>,
        @SerializedName("month")
        val month: String,
        @SerializedName("moodGph")
        val moodGraph: Graph?,
        @SerializedName("proGph")
        val progressGraph: Graph?,
        @SerializedName("progress")
        val progress: Progress?,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("weatherDtl")
        val weatherDetail: Weather?,
        @SerializedName("weekly")
        val weekly: List<Weekly>?,
        @SerializedName("year")
        val year: String
    )
}