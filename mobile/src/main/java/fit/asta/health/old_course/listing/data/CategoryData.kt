package fit.asta.health.old_course.listing.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryData(
    var uid: String = "",
    var title: String = "",
    var imgUrl: String = ""
) : Parcelable