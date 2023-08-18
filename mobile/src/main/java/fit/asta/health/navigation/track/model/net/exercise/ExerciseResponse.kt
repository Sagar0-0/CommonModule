package fit.asta.health.navigation.track.model.net.exercise

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.Graph
import fit.asta.health.navigation.track.model.net.common.Health
import fit.asta.health.navigation.track.model.net.common.MultiGraphParent
import fit.asta.health.navigation.track.model.net.common.Progress
import fit.asta.health.navigation.track.model.net.common.Status
import fit.asta.health.navigation.track.model.net.common.Weekly

data class ExerciseResponse(
    @SerializedName("data")
    val exerciseData: ExerciseData,
    @SerializedName("status")
    val status: Status
) {

    data class ExerciseData(
        @SerializedName("bdy")
        val bdy: Any,
        @SerializedName("bdyWeekly")
        val bdyWeekly: BdyWeekly,
        @SerializedName("bpGph")
        val bpGph: MultiGraphParent,
        @SerializedName("cal")
        val cal: Int,
        @SerializedName("dur")
        val dur: Int,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("exe")
        val exe: String,
        @SerializedName("hDtl")
        val hDtl: Health,
        @SerializedName("hrtRtGph")
        val hrtRtGph: Graph,
        @SerializedName("id")
        val id: String,
        @SerializedName("month")
        val month: String,
        @SerializedName("proGph")
        val proGph: Graph,
        @SerializedName("prog")
        val prog: Progress,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("sty")
        val sty: Any,
        @SerializedName("styWeekly")
        val styWeekly: StyWeekly,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("wk")
        val wk: Weekly,
        @SerializedName("year")
        val year: String
    )
}