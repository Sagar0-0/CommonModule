package fit.asta.health.navigation.track.data.remote.model.breathing

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.CardItem

data class BreathDetail(
    @SerializedName("breath")
    val breath: CardItem,
    @SerializedName("cal")
    val calories: CardItem,
    @SerializedName("in")
    val inhaled: CardItem
)