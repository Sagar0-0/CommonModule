package fit.asta.health.thirdparty.spotify.model.net.me.following


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artists(
    @SerializedName("cursors")
    val cursors: Cursors,
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/me/following?type=artist&limit=20
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String?, // null
    @SerializedName("total")
    val total: Int // 20
) : Parcelable