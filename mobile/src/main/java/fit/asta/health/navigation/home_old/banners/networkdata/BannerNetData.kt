package fit.asta.health.navigation.home_old.banners.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BannerNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("tid")
    val tid: String = "",
    @SerializedName("type")
    val type: Int = 0,
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("sub")
    val sub: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable