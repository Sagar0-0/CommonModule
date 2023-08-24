package fit.asta.health.thirdparty.spotify.data.model.recommendations


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seed(
    @SerializedName("afterFilteringSize")
    val afterFilteringSize: Int,
    @SerializedName("afterRelinkingSize")
    val afterRelinkingSize: Int,
    @SerializedName("href")
    val href: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("initialPoolSize")
    val initialPoolSize: Int,
    @SerializedName("type")
    val type: String
) : Parcelable