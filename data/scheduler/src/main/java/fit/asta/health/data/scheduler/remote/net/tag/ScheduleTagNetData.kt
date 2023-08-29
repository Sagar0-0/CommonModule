package fit.asta.health.data.scheduler.remote.net.tag

import com.google.gson.annotations.SerializedName

data class ScheduleTagNetData(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("tag")
    var tag: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("url")
    var url: String = ""
)

data class ScheduleTagResponse(
    @SerializedName("data")
    val `data`: ScheduleTagNetData,
    @SerializedName("statusDTO")
    val status: Status = Status()
)

data class ScheduleTagsResponse(
    @SerializedName("data")
    val `data`: List<ScheduleTagNetData> = listOf(),
    @SerializedName("statusDTO")
    val status: Status = Status()
)