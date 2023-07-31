package fit.asta.health.scheduler.model.net.spotify.library.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.net.spotify.search.ArtistList
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserFollowingArtist(
    @SerializedName("artists")
    val artistList: ArtistList
) : Parcelable