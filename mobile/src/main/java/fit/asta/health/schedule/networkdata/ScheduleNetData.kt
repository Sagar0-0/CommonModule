package fit.asta.health.schedule.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class ScheduleTimeNetData(
    @SerializedName("hr")
    var hr: Int? = null,
    @SerializedName("min")
    var min: Int? = null
)

data class ScheduleSessionNetData(
    @SerializedName("day")
    var day: String = "",
    @SerializedName("times")
    var times: List<ScheduleTimeNetData> = listOf()
)

data class ScheduleNetData(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("tagId")
    var tagId: String = "",
    @SerializedName("usrId")
    var usrId: String = "",
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("dsc")
    var dsc: String = "",
    @SerializedName("date")
    var date: String = "",
    @SerializedName("dur")
    var dur: Int = 0,
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("pry")
    var pry: Int = 0,
    @SerializedName("rmd")
    var rmd: Int = 0,
    @SerializedName("sns")
    var sns: List<ScheduleSessionNetData> = listOf()
)

data class ScheduleResponse(
    @SerializedName("data")
    val `data`: ScheduleNetData = ScheduleNetData(),
    @SerializedName("status")
    val status: Status = Status()
)