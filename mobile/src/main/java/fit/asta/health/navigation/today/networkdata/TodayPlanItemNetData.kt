package fit.asta.health.navigation.today.networkdata


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodayPlanItemNetData(
    @SerializedName("uid")
    val uid: String = "",
    @SerializedName("cid")
    var cid: String = "",
    @SerializedName("sid")
    var sid: String = "",
    @SerializedName("type")
    val type: Int = 1,
    @SerializedName("tag")
    val tag: String = "",
    @SerializedName("ttl")
    val ttl: String = "",
    @SerializedName("dsc")
    val dsc: String = "",
    @SerializedName("urlType")
    val urlType: Int = 0,
    @SerializedName("url")
    val url: String = "",
    @SerializedName("time")
    val time: Long = 0,
    @SerializedName("dur")
    val dur: Long = 0,
    @SerializedName("prog")
    val prog: Double = 0.0,
    @SerializedName("stat")
    val stat: Int = 0
) : Parcelable
