package fit.asta.health.data.scheduler.remote.net.tag

import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class NetCustomTag(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("uid")
    var uid: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("ttl")
    var title: String = "",
    @SerializedName("dsc")
    var description: String = "",
    @SerializedName("url")
    var url: String = "hi",
    @Transient
    var localUrl: Uri? = null,
) : Parcelable
