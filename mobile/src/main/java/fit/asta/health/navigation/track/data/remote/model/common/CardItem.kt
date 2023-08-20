package fit.asta.health.navigation.track.data.remote.model.common

import com.google.gson.annotations.SerializedName

data class CardItem(
    @SerializedName("avg")
    val avg: Double,
    @SerializedName("unit")
    val unit: String
)