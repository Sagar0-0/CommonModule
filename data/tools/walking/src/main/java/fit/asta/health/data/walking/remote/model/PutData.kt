package fit.asta.health.data.walking.remote.model


import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc


data class PutData(
    @SerializedName("code")
    val code: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("prc")
    val prc: List<Prc>,
    @SerializedName("sType")
    val sType: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("tgt")
    val tgt: Target,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("wea")
    val wea: Boolean
)