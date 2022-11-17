package fit.asta.health.thirdparty.spotify.model.net.categories


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/browse/categories/toplists
    @SerializedName("icons")
    val icons: List<Icon>,
    @SerializedName("id")
    val id: String, // toplists
    @SerializedName("name")
    val name: String // Top Lists
) : Parcelable