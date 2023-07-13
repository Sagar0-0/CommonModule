package fit.asta.health.tools.breathing.model.network.request


import com.google.gson.annotations.SerializedName

data class NetPost(
    @SerializedName("breath")
    val breath: Int,
    @SerializedName("cal")
    val calories: Int,
    @SerializedName("dur")
    val duration: Int,
    @SerializedName("ex")
    val ex: List<String>,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("in")
    val inX: Int,
    @SerializedName("lvl")
    val level: String,
    @SerializedName("uid")
    val uid: String
)