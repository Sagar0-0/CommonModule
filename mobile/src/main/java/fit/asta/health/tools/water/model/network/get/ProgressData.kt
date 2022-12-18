package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class ProgressData(
    @SerializedName("con")
    val con: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("rcm")
    val rcm: Double,
    @SerializedName("rem")
    val rem: Int,
    @SerializedName("tgt")
    val tgt: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("uid")
    val uid: String
)