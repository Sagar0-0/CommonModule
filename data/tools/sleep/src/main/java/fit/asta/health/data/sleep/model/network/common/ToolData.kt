package fit.asta.health.data.sleep.model.network.common

import com.google.gson.annotations.SerializedName

data class ToolData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String
)