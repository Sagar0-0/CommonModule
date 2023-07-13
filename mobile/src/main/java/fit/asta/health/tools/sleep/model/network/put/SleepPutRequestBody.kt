package fit.asta.health.tools.sleep.model.network.put

import com.google.gson.annotations.SerializedName
import fit.asta.health.tools.sleep.model.network.common.Prc

data class SleepPutRequestBody(
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