package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.StepsDetails

data class Progress(
    @SerializedName("achieved")
    val achieved: StepsDetails,
    @SerializedName("target")
    val target: StepsDetails
)