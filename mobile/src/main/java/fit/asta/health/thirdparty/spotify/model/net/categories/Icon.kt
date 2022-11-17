package fit.asta.health.thirdparty.spotify.model.net.categories


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    @SerializedName("height")
    val height: Int, // 275
    @SerializedName("url")
    val url: String, // https://t.scdn.co/media/derived/toplists_11160599e6a04ac5d6f2757f5511778f_0_0_275_275.jpg
    @SerializedName("width")
    val width: Int // 275
) : Parcelable