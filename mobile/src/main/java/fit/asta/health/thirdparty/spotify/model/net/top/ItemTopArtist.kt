package fit.asta.health.thirdparty.spotify.model.net.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.netx.common.ExternalUrlsX
import fit.asta.health.thirdparty.spotify.model.netx.common.FollowersX
import fit.asta.health.thirdparty.spotify.model.netx.common.ImageX
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemTopArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("followers")
    val followers: FollowersX,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/4zCH9qm4R2DADamUHMCa6O
    @SerializedName("id")
    val id: String, // 4zCH9qm4R2DADamUHMCa6O
    @SerializedName("images")
    val images: List<ImageX>,
    @SerializedName("name")
    val name: String, // Anirudh Ravichander
    @SerializedName("popularity")
    val popularity: Int, // 82
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:4zCH9qm4R2DADamUHMCa6O
) : Parcelable