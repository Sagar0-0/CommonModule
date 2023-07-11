package fit.asta.health.tools.breathing.model.network.request


import com.google.gson.annotations.SerializedName

data class CustomRatioPost(
    @SerializedName("id")
    val id: String,
    @SerializedName("inH")
    val inhaleH: Int,
    @SerializedName("in")
    val inhale: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("out")
    val `out`: Int,
    @SerializedName("outH")
    val outH: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String
)