package fit.asta.health.thirdparty.spotify.model.net.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("height")
    val height: Int, // 640
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab67616d0000b2730341025734052bc0a025f427
    @SerializedName("width")
    val width: Int // 640
) : Parcelable