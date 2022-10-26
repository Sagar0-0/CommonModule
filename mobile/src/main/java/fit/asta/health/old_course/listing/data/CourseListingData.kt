package fit.asta.health.old_course.listing.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CourseListingData(
    var categoryTitle: String = "",
    var categoryId: String = ""
) : Parcelable
