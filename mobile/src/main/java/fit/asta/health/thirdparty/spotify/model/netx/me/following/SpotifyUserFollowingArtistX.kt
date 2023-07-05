package fit.asta.health.thirdparty.spotify.model.netx.me.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.search.ArtistListX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyUserFollowingArtistX(
    @SerializedName("artists")
    val artists: ArtistListX
) : Parcelable