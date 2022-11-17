package fit.asta.health.thirdparty.spotify.model.net.categories


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotifyBrowseCategoriesModel(
    @SerializedName("categories")
    val categories: Categories
) : Parcelable