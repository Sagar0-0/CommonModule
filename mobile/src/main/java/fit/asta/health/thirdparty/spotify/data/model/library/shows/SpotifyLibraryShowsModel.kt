package fit.asta.health.thirdparty.spotify.data.model.library.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyLibraryShowsModel(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val showList: List<ShowParent>,
    @SerializedName("limit")
    val limit: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("previous")
    val previous: String,
    @SerializedName("total")
    val total: Int
) : Parcelable