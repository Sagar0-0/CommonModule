package fit.asta.health.subscription.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CarouselData(
    var title: String = "",
    var description: String = "",
    var url: String = ""
) : Parcelable
