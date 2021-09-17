package fit.asta.health.course.listing.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CourseListingData(
    var categoryTitle: String = "",
    var categoryId: String = ""
) : Parcelable
