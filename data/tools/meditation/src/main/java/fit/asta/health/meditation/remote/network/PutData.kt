package fit.asta.health.meditation.remote.network


import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc

data class PutData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("type")
    val type: Int,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)