package fit.asta.health.navigation.track.data.remote.model.sleep

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class SleepResponse(
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("goalGph")
    val goalGraph: Graph?,
    @SerializedName("id")
    val id: String,
    @SerializedName("mid")
    val mid: List<String>,
    @SerializedName("month")
    val month: String,
    @SerializedName("moodGph")
    val moodGraph: Graph?,
    @SerializedName("progress")
    val progress: Progress?,
    @SerializedName("slpDuration")
    val sleepDurationGraph: Graph?,
    @SerializedName("slpRatio")
    val sleepRatio: SlpRatio?,
    @SerializedName("slpReg")
    val sleepRegularityGraph: Graph?,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("weekly")
    val weekly: List<Weekly>?,
    @SerializedName("year")
    val year: String
)