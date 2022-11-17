package fit.asta.health.thirdparty.spotify.model.net.me.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("height")
    val height: Int, // 640
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab6761610000e5eb8bcbc7079a7a8140f4377218
    @SerializedName("width")
    val width: Int // 640
) : Parcelable