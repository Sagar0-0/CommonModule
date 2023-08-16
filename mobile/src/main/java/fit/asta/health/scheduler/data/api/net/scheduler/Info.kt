package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    @SerializedName("des")
    val description: String, // des
    @SerializedName("name")
    var name: String, // power nap
    @SerializedName("tag")
    val tag: String, // nap 1
    @SerializedName("tid")
    val tagId: String, // 6310c0782aeef2b4b3684a6d
    @SerializedName("url")
    val url: String // url
) : Parcelable