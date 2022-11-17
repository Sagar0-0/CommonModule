package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.thirdparty.spotify.model.net.top.ItemTopArtist
import kotlinx.parcelize.Parcelize

@Parcelize
data class Artists(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/search?query=n&type=artist&include_external=audio&offset=0&limit=20
    @SerializedName("items")
    val items: List<ItemTopArtist>,
    @SerializedName("limit")
    val limit: Int, // 20
    @SerializedName("next")
    val next: String, // https://api.spotify.com/v1/search?query=n&type=artist&include_external=audio&offset=20&limit=20
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("previous")
    val previous: String?, // null
    @SerializedName("total")
    val total: Int // 10047
) : Parcelable