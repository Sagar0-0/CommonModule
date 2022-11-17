package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArtistX(
    @SerializedName("external_urls")
    val externalUrls: ExternalUrlsX,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/5JjGzUYs91P60FRqXnZ87M
    @SerializedName("id")
    val id: String, // 5JjGzUYs91P60FRqXnZ87M
    @SerializedName("name")
    val name: String, // DJ 2L de Vila Velha
    @SerializedName("type")
    val type: String, // artist
    @SerializedName("uri")
    val uri: String // spotify:artist:5JjGzUYs91P60FRqXnZ87M
) : Parcelable