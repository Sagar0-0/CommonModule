package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrls(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/playlist/37i9dQZF1DX7cLxqtNO3zl
) : Parcelable