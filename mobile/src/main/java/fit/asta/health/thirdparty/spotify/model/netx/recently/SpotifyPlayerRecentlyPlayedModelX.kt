package fit.asta.health.thirdparty.spotify.model.netx.recently


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyPlayerRecentlyPlayedModelX(
    @SerializedName("cursors")
    val cursors: CursorsX,
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<ItemX>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String
) : Parcelable