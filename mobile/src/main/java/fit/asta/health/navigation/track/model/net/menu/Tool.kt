package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName

data class Tool(
    @SerializedName("achieved")
    val achieved: Float,
    @SerializedName("description")
    val description: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("progress")
    val progress: Float,
    @SerializedName("remaining")
    val remaining: Float,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("target")
    val target: Float,
    @SerializedName("title")
    val title: String,
    @SerializedName("vitaminD")
    val vitaminD: Float
)