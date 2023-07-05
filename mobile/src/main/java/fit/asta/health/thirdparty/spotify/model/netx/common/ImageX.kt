package fit.asta.health.thirdparty.spotify.model.netx.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageX(
    @SerializedName("height")
    val height: Int, // 300
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab67616d00001e02ff9ca10b55ce82ae553c8228
    @SerializedName("width")
    val width: Int // 300
) : Parcelable