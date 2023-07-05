package fit.asta.health.thirdparty.spotify.model.netx.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemX(
    @SerializedName("added_at")
    val addedAt: String,
    @SerializedName("album")
    val album: AlbumX
) : Parcelable