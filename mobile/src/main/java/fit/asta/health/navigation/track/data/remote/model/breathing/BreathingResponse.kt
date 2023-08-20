package fit.asta.health.navigation.track.data.remote.model.breathing

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.Graph
import fit.asta.health.navigation.track.data.remote.model.common.Health
import fit.asta.health.navigation.track.data.remote.model.common.MultiGraphParent
import fit.asta.health.navigation.track.data.remote.model.common.Progress
import fit.asta.health.navigation.track.data.remote.model.common.Status
import fit.asta.health.navigation.track.data.remote.model.common.Weather
import fit.asta.health.navigation.track.data.remote.model.common.Weekly

data class BreathingResponse(
    @SerializedName("data")
    val breathingData: BreathingData,
    @SerializedName("status")
    val status: Status
) {

    data class BreathingData(
        @SerializedName("air")
        val air: Air?,
        @SerializedName("airGph")
        val airGraph: Graph?,
        @SerializedName("bDtl")
        val breathDetail: BreathDetail?,
        @SerializedName("bpGph")
        val bloodPressureGraph: MultiGraphParent?,
        @SerializedName("endDate")
        val endDate: String,
        @SerializedName("hDtl")
        val health: Health?,
        @SerializedName("hrtRtGph")
        val heartRateGraph: Graph?,
        @SerializedName("id")
        val id: String,
        @SerializedName("mdGph")
        val moodGraph: Graph?,
        @SerializedName("mid")
        val mid: List<String>?,
        @SerializedName("month")
        val month: String,
        @SerializedName("proGph")
        val progressGraph: Graph?,
        @SerializedName("prog")
        val progress: Progress?,
        @SerializedName("startDate")
        val startDate: String,
        @SerializedName("uid")
        val uid: String,
        @SerializedName("wk")
        val weekly: List<Weekly>?,
        @SerializedName("wxDtl")
        val weather: Weather?,
        @SerializedName("year")
        val year: String
    )
}