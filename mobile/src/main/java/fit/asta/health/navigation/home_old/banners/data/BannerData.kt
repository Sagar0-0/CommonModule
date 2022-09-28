package fit.asta.health.navigation.home_old.banners.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannerData(
    var uid: String = "",
    var typeId: String = "",
    var type: Int = 0,
    var title: String = "",
    var subTitle: String = "",
    var url: String = ""
) : Parcelable