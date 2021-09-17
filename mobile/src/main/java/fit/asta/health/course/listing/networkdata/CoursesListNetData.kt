package fit.asta.health.course.listing.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CoursesListNetData(
    @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<CourseIndexNetData> = listOf()
)
