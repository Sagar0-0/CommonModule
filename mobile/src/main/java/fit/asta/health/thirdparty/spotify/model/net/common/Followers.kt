package fit.asta.health.thirdparty.spotify.model.net.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    @SerializedName("href")
    val href: String?, // string
    @SerializedName("total")
    val total: Int // 0
) : Parcelable