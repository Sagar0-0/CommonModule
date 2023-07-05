package fit.asta.health.thirdparty.spotify.model.netx.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ArtistX
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyTopArtistsModelX(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<ArtistX>,
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