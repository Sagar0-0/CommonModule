package fit.asta.health.navigation.track.model.net.breathing

import com.google.gson.annotations.SerializedName

data class Air(
    @SerializedName("lvl")
    val lvl: Float,
    @SerializedName("meta")
    val meta: Meta,
    @SerializedName("sts")
    val sts: String,
    @SerializedName("unit")
    val unit: String
) {

    data class Meta(
        @SerializedName("max")
        val max: Float,
        @SerializedName("min")
        val min: Float
    )
}