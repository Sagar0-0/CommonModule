package fit.asta.health.navigation.track.data.remote.model.exercise

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Health
import fit.asta.health.navigation.track.data.remote.model.common.MultiGraphParent
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Status
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class ExerciseResponse(
    @SerializedName("data")
    val exerciseData: ExerciseData,
    @SerializedName("status")
    val status: Status
) {

    data class ExerciseData(
        @SerializedName("bdy")
        val bdy: List<String>?,
        @SerializedName("bdyWeekly")
        val bdyWeekly: BdyWeekly?,
        @SerializedName("cal")
        val cal: Float,
        @SerializedName("dur")
        val dur: Float,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("sty")
        val sty: List<String>?,
        @SerializedName("styWeekly")
        val styWeekly: StyWeekly?,

        // Used Variables
        @SerializedName("id")
        val id: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("exe")
        val exe: String,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("year")
        val year: String,
        @SerializedName("prog")
        val dailyProgress: Progress?,
        @SerializedName("wk")
        val weekly: List<Weekly>?,
        @SerializedName("proGph")
        val progressGraph: Graph?,
        @SerializedName("hDtl")
        val heartDetail: Health?,
        @SerializedName("hrtRtGph")
        val heartRateGraph: Graph?,
        @SerializedName("bpGph")
        val bloodPressureGraph: MultiGraphParent?,
    )
}