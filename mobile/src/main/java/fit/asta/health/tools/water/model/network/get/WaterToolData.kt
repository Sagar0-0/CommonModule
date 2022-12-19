package fit.asta.health.tools.water.model.network.get


import com.google.gson.annotations.SerializedName

data class WaterToolData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: Any,
    @SerializedName("tgt")
    val tgt: String,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)