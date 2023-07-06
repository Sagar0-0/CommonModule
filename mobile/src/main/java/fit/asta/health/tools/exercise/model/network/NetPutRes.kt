package fit.asta.health.tools.exercise.model.network


import com.google.gson.annotations.SerializedName

data class NetPutRes(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<PrcX>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val weather: Boolean
)