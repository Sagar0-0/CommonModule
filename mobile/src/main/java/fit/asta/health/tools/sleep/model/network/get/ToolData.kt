package fit.asta.health.tools.sleep.model.network.get

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Prc

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