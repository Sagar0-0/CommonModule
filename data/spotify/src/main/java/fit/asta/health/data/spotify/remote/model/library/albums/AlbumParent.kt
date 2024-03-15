package fit.asta.health.data.spotify.remote.model.library.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.remote.model.common.Album
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("album")
    val album: Album
) : Parcelable