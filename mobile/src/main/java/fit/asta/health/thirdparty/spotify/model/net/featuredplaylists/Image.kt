package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @SerializedName("height")
    val height: Int, // null
    @SerializedName("url")
    val url: String, // https://i.scdn.co/image/ab67706f0000000384696e0bd9318a598cc9373d
    @SerializedName("width")
    val width: Int // null
) : Parcelable