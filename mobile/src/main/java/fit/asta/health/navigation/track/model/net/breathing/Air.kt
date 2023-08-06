package fit.asta.health.navigation.track.model.net.breathing

import com.google.gson.annotations.SerializedName

data class Air(
    @SerializedName("lvl")
    val lvl: Int,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("sts")
    val sts: String,
    @SerializedName("unit")
    val unit: String
) {

    data class Meta(
        @SerializedName("max")
        val max: String,
        @SerializedName("min")
        val min: String
    )
}