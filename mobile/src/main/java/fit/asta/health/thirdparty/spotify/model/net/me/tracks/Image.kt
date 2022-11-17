package fit.asta.health.thirdparty.spotify.model.net.me.tracks


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("height")
    val height: Int, // 640
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab67616d0000b273b6c681dcfe4398b523bc2bbe
    @SerializedName("width")
    val width: Int // 640
) : Parcelable