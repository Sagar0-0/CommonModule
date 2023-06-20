package fit.asta.health.navigation.track.model.network

import com.google.gson.annotations.SerializedName

data class NetAirDetail(
    @SerializedName("sts")
    val status: String,
    @SerializedName("lvl")
    val level: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("meta")
    val airMeta: Meta
)

data class Meta(
    @SerializedName("min")
    val min: String,
    @SerializedName("max")
    val max: String
)