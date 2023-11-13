package fit.asta.health.navigation.track.data.remote.model.step

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.BmiData
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Health
import fit.asta.health.navigation.track.data.remote.model.common.MultiGraphParent
import fit.asta.health.navigation.track.data.remote.model.common.StepsDetails
import fit.asta.health.navigation.track.data.remote.model.common.Weather
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class StepsResponse(
    @SerializedName("bmi")
    val bmiData: BmiData?,
    @SerializedName("bpGph")
    val bloodPressureGraph: MultiGraphParent?,
    @SerializedName("disGph")
    val distanceGraph: Graph?,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("health")
    val health: Health?,
    @SerializedName("id")
    val id: String,
    @SerializedName("intGph")
    val intensityGraph: Graph?,
    @SerializedName("mid")
    val mid: List<String>,
    @SerializedName("month")
    val month: String,
    @SerializedName("moodGph")
    val moodGraph: Graph?,
    @SerializedName("progress")
    val progress: Progress?,
    @SerializedName("speed")
    val speed: Speed?,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("stepDtl")
    val stepsDetail: StepsDetails?,
    @SerializedName("stpGph")
    val stepGraph: Graph?,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("weather")
    val weather: Weather?,
    @SerializedName("weekly")
    val weekly: List<Weekly>?,
    @SerializedName("year")
    val year: String
)