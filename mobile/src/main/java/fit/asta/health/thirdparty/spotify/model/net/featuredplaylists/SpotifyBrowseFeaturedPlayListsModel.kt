package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyBrowseFeaturedPlayListsModel(
    @SerializedName("message")
    val message: String, // Editor's picks
    @SerializedName("playlists")
    val playlists: Playlists
) : Parcelable