package fit.asta.health.old.course.listing.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseIndexData(
    var uid: String = "",
    var categoryId: String = "",
    var title: String = "",
    var subTitle: String = "",
    var url: String = "",
    var audienceLevel: String = "",
    var intensity: String = "",
    var duration: String = ""
) : Parcelable