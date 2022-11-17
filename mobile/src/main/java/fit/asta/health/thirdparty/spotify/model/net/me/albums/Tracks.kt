package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/albums/6OiuCvQifZVGD0DANTN2IL/tracks?offset=0&limit=50
    @SerializedName("items")
    val items: List<ItemX>,
    @SerializedName("limit")
    val limit: Int, // 50
    @SerializedName("next")
    val next: String, // null
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String, // null
    @SerializedName("total")
    val total: Int // 1
) : Parcelable