package fit.asta.health.thirdparty.spotify.model.net.featuredplaylists


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("collaborative")
    val collaborative: Boolean, // false
    @SerializedName("description")
    val description: String, // From India to MENA, these hits will make you dance.
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/37i9dQZF1DX7cLxqtNO3zl
    @SerializedName("id")
    val id: String, // 37i9dQZF1DX7cLxqtNO3zl
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // Bollywood Araby
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("primary_color")
    val primaryColor: String, // null
    @SerializedName("public")
    val `public`: String, // null
    @SerializedName("snapshot_id")
    val snapshotId: String, // MTY1NDY4MjYyNywwMDAwMDAwMDNkN2Y0MDBmMzQ4MzQ4YzRiNTk0YWYyYTQ1MzRjNWM3
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String, // playlist
    @SerializedName("uri")
    val uri: String // spotify:playlist:37i9dQZF1DX7cLxqtNO3zl
) : Parcelable