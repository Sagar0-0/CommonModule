package fit.asta.health.tools.breathing.model.network


import com.google.gson.annotations.SerializedName

data class Rto(
    @SerializedName("inH")
    val inH: Int,
    @SerializedName("in")
    val inX: Int,
    @SerializedName("out")
    val `out`: Int,
    @SerializedName("outH")
    val outH: Int
)