package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/37i9dQZF1DX7cLxqtNO3zl/tracks
    @SerializedName("total")
    val total: Int // 50
) : Parcelable