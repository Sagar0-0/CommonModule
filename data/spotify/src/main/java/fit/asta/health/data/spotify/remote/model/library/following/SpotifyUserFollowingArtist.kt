package fit.asta.health.data.spotify.remote.model.library.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.remote.model.search.ArtistList
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserFollowingArtist(
    @SerializedName("artists")
    val artistList: ArtistList
) : Parcelable