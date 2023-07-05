package fit.asta.health.thirdparty.spotify.model.netx.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeedX(
    @SerializedName("afterFilteringSize")
    val afterFilteringSize: Int, // 250
    @SerializedName("afterRelinkingSize")
    val afterRelinkingSize: Int, // 250
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/artists/4NHQUGzhtTLFvgF5SZesLK
    @SerializedName("id")
    val id: String, // 4NHQUGzhtTLFvgF5SZesLK
    @SerializedName("initialPoolSize")
    val initialPoolSize: Int, // 250
    @SerializedName("type")
    val type: String // ARTIST
) : Parcelable