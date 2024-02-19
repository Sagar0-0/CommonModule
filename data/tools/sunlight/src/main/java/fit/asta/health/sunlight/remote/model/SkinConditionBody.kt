package fit.asta.health.sunlight.remote.model

import com.google.gson.annotations.SerializedName
import fit.asta.health.common.utils.Prc


data class SkinConditionBody (
    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("uid"  ) var uid  : String? = null,
    @SerializedName("type" ) var type : Int?    = null,
    @SerializedName("code" ) var code : String? = null,
    @SerializedName("prc" ) var prc : List<Prc>? = null,
    @SerializedName("sup" ) var sup : Sup? = null,
)