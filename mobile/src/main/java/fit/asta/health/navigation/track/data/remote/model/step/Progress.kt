package fit.asta.health.navigation.track.data.remote.model.step

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.data.remote.model.common.StepsDetails

data class Progress(
    @SerializedName("achieved")
    val achieved: StepsDetails,
    @SerializedName("target")
    val target: StepsDetails
)