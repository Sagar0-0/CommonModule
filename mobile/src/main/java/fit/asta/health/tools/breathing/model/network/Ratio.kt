package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Ratio(
    @SerializedName("inH")
    val inhaleHold: Int,
    @SerializedName("in")
    val inhale: Int,
    @SerializedName("out")
    val exhale: Int,
    @SerializedName("outH")
    val exhaleHold: Int
)