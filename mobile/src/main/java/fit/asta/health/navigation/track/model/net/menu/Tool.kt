package fit.asta.health.navigation.track.model.net.menu

import com.google.gson.annotations.SerializedName

data class Tool(
    @SerializedName("title")
    val title: String,
    @SerializedName("detail")
    val detail: ToolDetail
) {

    data class ToolDetail(
        @SerializedName("toolProgress")
        val toolProgress: ToolProgress,
        @SerializedName("vitD")
        val vitD: VitD,
        @SerializedName("timing")
        val timing: Timing,
        @SerializedName("description")
        val description: String
    ) {

        data class ToolProgress(
            @SerializedName("progress")
            val progress: Float,
            @SerializedName("achieved")
            val achieved: Float,
            @SerializedName("target")
            val target: Float,
            @SerializedName("remaining")
            val remaining: Float,
            @SerializedName("unit")
            val unit: String,
        )

        data class VitD(
            @SerializedName("avg")
            val avg: String,
            @SerializedName("unit")
            val unit: String
        )

        data class Timing(
            @SerializedName("startTime")
            val startTime: String,
            @SerializedName("endTime")
            val endTime: String
        )
    }
}