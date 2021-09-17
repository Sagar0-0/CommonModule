package fit.asta.health.course.details.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CourseDetailsResponse(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val data: CourseDetailsNetData = CourseDetailsNetData()
)
