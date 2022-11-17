package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/show/6ZcvVBPQ2ToLXEWVbaw59P
) : Parcelable