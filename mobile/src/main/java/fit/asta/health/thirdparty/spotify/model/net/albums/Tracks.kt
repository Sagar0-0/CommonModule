package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy/tracks?offset=0&limit=50
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("limit")
    val limit: Int, // 50
    @SerializedName("next")
    val next: String?, // null
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String?, // null
    @SerializedName("total")
    val total: Int // 18
) : Parcelable