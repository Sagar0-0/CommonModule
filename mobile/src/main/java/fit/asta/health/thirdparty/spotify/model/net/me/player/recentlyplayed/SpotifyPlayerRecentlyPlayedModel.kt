package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyPlayerRecentlyPlayedModel(
    @SerializedName("cursors")
    val cursors: Cursors,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/me/player/recently-played
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String // https://api.spotify.com/v1/me/player/recently-played?before=1662901690206
) : Parcelable