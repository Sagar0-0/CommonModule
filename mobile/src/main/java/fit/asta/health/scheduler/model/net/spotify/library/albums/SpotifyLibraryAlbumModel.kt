package fit.asta.health.scheduler.model.net.spotify.library.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryAlbumModel(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val albumList: List<AlbumParent>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("total")
    val total: Int
) : Parcelable