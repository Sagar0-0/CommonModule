package fit.asta.health.old.course.listing.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryData(
    var uid: String = "",
    var title: String = "",
    var imgUrl: String = ""
) : Parcelable