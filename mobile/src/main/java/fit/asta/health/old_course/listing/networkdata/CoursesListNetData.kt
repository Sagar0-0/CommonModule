package fit.asta.health.old_course.listing.networkdata

import com.google.gson.annotations.SerializedName
import fit.asta.health.network.data.Status

data class CoursesListNetData(
    @SerializedName("statusDTO")
    val status: Status = Status(),
    @SerializedName("data")
    val `data`: List<CourseIndexNetData> = listOf()
)
