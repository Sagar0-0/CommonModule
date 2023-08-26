package fit.asta.health.data.spotify.model.library.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.spotify.model.common.Album
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumParent(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("album")
    val album: Album
) : Parcelable