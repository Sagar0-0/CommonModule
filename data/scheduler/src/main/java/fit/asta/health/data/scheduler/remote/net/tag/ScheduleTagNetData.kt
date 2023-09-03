package fit.asta.health.data.scheduler.remote.net.tag

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScheduleTagNetData(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("tag")
    var tag: String = "",
    @SerializedName("type")
    var type: Int = 0,
    @SerializedName("url")
    var url: String = "",
    @Transient
    var localUrl: Uri? = null,
) : Parcelable

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