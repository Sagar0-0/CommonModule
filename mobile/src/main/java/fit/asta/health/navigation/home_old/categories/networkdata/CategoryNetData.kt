package fit.asta.health.navigation.home_old.categories.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class CategoryNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("url")
    val url: String = ""
) : Parcelable