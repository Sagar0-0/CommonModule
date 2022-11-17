package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("height")
    val height: Int, // 640
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab67616d0000b2732c5b24ecfa39523a75c993c4
    @SerializedName("width")
    val width: Int // 640
) : Parcelable