package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageX(
    @SerializedName("height")
    val height: Int, // 640
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab6765630000ba8aadead612fce9a9802ac667c4
    @SerializedName("width")
    val width: Int // 640
) : Parcelable