package fit.asta.health.thirdparty.spotify.model.net.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.common.ExternalUrls
import fit.asta.health.thirdparty.spotify.model.net.common.Image
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserPlaylistItem(
    @SerializedName("collaborative")
    val collaborative: Boolean, // false
    @SerializedName("description")
    val description: String,
    @SerializedName("external_urls")
    val externalUrls: ExternalUrls,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/4AyeT23Lb3jjt0vDewAp4y
    @SerializedName("id")
    val id: String, // 4AyeT23Lb3jjt0vDewAp4y
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("name")
    val name: String, // Aesthetic
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("primary_color")
    val primaryColor: String?, // null
    @SerializedName("public")
    val `public`: Boolean, // false
    @SerializedName("snapshot_id")
    val snapshotId: String, // MjIsODdmMWQ2OTVjNjMzY2EzYTdjODIzODYyZTFiZGU3MzJiMzQzM2QzYg==
    @SerializedName("tracks")
    val tracks: Tracks,
    @SerializedName("type")
    val type: String, // playlist
    @SerializedName("uri")
    val uri: String // spotify:playlist:4AyeT23Lb3jjt0vDewAp4y
) : Parcelable