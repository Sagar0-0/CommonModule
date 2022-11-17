package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/37i9dQZF1EO3XeqYhzPGVS/tracks
    @SerializedName("total")
    val total: Int // 30
) : Parcelable