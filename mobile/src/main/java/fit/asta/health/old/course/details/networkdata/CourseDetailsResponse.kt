package fit.asta.health.old.course.details.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CourseDetailsResponse(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val data: CourseDetailsNetData = CourseDetailsNetData()
)
