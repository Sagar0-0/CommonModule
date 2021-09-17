package fit.asta.health.schedule.tags.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

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
    @SerializedName("status")
    val status: Status = Status()
)

data class ScheduleTagsResponse(
    @SerializedName("data")
    val `data`: List<ScheduleTagNetData> = listOf(),
    @SerializedName("status")
    val status: Status = Status()
)