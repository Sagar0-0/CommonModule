package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName

data class Tool(
    @SerializedName("achieved")
    val achieved: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("progress")
    val progress: Int,
    @SerializedName("remaining")
    val remaining: Int,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("target")
    val target: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("vitaminD")
    val vitaminD: Int
)