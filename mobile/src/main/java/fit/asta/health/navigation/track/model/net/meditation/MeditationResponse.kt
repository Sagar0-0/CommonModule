package fit.asta.health.navigation.track.model.net.meditation

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.Graph
import fit.asta.health.navigation.track.model.net.common.Status
import fit.asta.health.navigation.track.model.net.common.Health
import fit.asta.health.navigation.track.model.net.common.MultiGraphParent
import fit.asta.health.navigation.track.model.net.common.Progress
import fit.asta.health.navigation.track.model.net.common.Weekly
import fit.asta.health.navigation.track.model.net.common.Weather

data class MeditationResponse(
    @SerializedName("data")
    val meditationData: MeditationData,
    @SerializedName("status")
    val status: Status
) {

    data class MeditationData(
        @SerializedName("bpGph")
        val bpGph: MultiGraphParent,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("healthDtl")
        val healthDtl: Health,
        @SerializedName("hrtRateGph")
        val hrtRateGph: Graph,
        @SerializedName("id")
        val id: String,
        @SerializedName("mid")
        val mid: List<String>,
        @SerializedName("month")
        val month: String,
        @SerializedName("moodGph")
        val moodGph: Graph,
        @SerializedName("proGph")
        val proGph: Graph,
        @SerializedName("progress")
        val progress: Progress,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("weatherDtl")
        val weatherDtl: Weather,
        @SerializedName("weekly")
        val weekly: List<Weekly>,
        @SerializedName("year")
        val year: String
    )
}