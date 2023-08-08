package fit.asta.health.navigation.track.model.net.step

import com.google.gson.annotations.SerializedName

data class StepsDetails(
    @SerializedName("dis")
    val distance: Distance,
    @SerializedName("dur")
    val duration: Duration,
    @SerializedName("steps")
    val steps: Steps
) {

    data class Distance(
        @SerializedName("dis")
        val dis: Float,
        @SerializedName("unit")
        val unit: String
    )

    data class Duration(
        @SerializedName("dur")
        val dur: Float,
        @SerializedName("unit")
        val unit: String
    )

    data class Steps(
        @SerializedName("Steps")
        val steps: Int,
        @SerializedName("unit")
        val unit: String
    )
}