package fit.asta.health.thirdparty.spotify.model.netx.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.AlbumX
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumListX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val albumItems: List<AlbumX>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("total")
    val total: Int
) : Parcelable