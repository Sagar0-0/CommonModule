package fit.asta.health.course.listing.networkdata

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CourseIndexNetData(
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("cid")
    var cid: String = "",
    @SerializedName("ttl")
    var ttl: String = "",
    @SerializedName("dsc")
    var dsc: String = "",
    @SerializedName("url")
    var url: String = "",
    @SerializedName("lvl")
    var lvl: String = "",
    @SerializedName("int")
    var int: String = "",
    @SerializedName("dur")
    var dur: String = ""
) : Parcelable