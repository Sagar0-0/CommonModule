package fit.asta.health.thirdparty.spotify.model.net.top


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.model.net.common.Followers
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemTopArtist(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("followers")
    val followers: Followers,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/4zCH9qm4R2DADamUHMCa6O
    @SerializedName("id")
    val id: String, // 4zCH9qm4R2DADamUHMCa6O
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // Anirudh Ravichander
    @SerializedName("popularity")
    val popularity: Int, // 82
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:4zCH9qm4R2DADamUHMCa6O
) : Parcelable