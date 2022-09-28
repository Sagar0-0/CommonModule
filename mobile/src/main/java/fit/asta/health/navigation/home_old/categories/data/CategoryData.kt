package fit.asta.health.navigation.home_old.categories.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryData(
    var uid: String = "",
    var title: String = "",
    var imgUrl: String = ""
) : Parcelable