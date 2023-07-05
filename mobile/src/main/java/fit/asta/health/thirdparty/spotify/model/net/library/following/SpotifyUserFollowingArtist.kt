package fit.asta.health.thirdparty.spotify.model.net.library.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.search.ArtistList
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserFollowingArtist(
    @SerializedName("artists")
    val artistList: ArtistList
) : Parcelable