package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episodes(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/search?query=n&type=episode&include_external=audio&offset=0&limit=20
    @SerializedName("items")
    val items: List<EpisodeItem>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // https://api.spotify.com/v1/search?query=n&type=episode&include_external=audio&offset=20&limit=20
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String?, // null
    @SerializedName("total")
    val total: Int // 10017
) : Parcelable