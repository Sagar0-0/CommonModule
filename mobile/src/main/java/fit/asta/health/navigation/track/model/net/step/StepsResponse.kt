package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.Graph
import fit.asta.health.navigation.track.model.net.common.MultiGraphParent
import fit.asta.health.navigation.track.model.net.common.Progress
import fit.asta.health.navigation.track.model.net.common.Status
import fit.asta.health.navigation.track.model.net.common.Weekly

data class StepsResponse(
    @SerializedName("data")
    val stepsData: StepsData,
    @SerializedName("status")
    val status: Status
) {

    data class StepsData(
        @SerializedName("bmi")
        val bmiData: BmiData,
        @SerializedName("bpGph")
        val bpGph: MultiGraphParent,
        @SerializedName("disGph")
        val disGph: Graph,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("health")
        val health: Health,
        @SerializedName("id")
        val id: String,
        @SerializedName("intGph")
        val intGph: Graph,
        @SerializedName("mid")
        val mid: List<String>,
        @SerializedName("month")
        val month: String,
        @SerializedName("moodGph")
        val moodGph: Graph,
        @SerializedName("progress")
        val progress: Progress,
        @SerializedName("speed")
        val speed: Speed,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("stepDtl")
        val stepDtl: StepsDetails,
        @SerializedName("stpGph")
        val stpGph: Graph,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("weather")
        val weather: Weather,
        @SerializedName("weekly")
        val weekly: List<Weekly>,
        @SerializedName("year")
        val year: String
    )

}