package fit.asta.health.thirdparty.spotify.model.net.library.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.Album
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("album")
    val album: Album
) : Parcelable