package fit.asta.health.old_subscription.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarouselData(
    var title: String = "",
    var description: String = "",
    var url: String = ""
) : Parcelable
