package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName
import fit.asta.health.navigation.track.model.net.common.StepsDetails

data class Walking(
    @SerializedName("achieved")
    val achieved: StepsDetails,
    @SerializedName("description")
    val description: Description,
    @SerializedName("progress")
    val progress: StepsDetails,
    @SerializedName("remaining")
    val remaining: StepsDetails,
    @SerializedName("target")
    val target: StepsDetails,
    @SerializedName("title")
    val title: String,
    @SerializedName("vitaminD")
    val vitaminD: Float
) {

    data class Description(
        @SerializedName("distanceDescription")
        val distanceDescription: String,
        @SerializedName("durationDescription")
        val durationDescription: String,
        @SerializedName("stepsDescription")
        val stepsDescription: String
    )
}