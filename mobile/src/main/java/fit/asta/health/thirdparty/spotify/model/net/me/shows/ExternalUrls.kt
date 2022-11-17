package fit.asta.health.thirdparty.spotify.model.net.me.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrls(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/show/6ZcvVBPQ2ToLXEWVbaw59P
) : Parcelable