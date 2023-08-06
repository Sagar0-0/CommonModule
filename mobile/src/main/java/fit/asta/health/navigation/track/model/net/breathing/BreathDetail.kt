package fit.asta.health.navigation.track.model.net.breathing

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.CardItem

data class BreathDetail(
    @SerializedName("breath")
    val breath: CardItem,
    @SerializedName("cal")
    val cal: CardItem,
    @SerializedName("in")
    val inhaled: CardItem
)