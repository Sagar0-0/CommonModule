package fit.asta.health.thirdparty.spotify.model.net.search


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    @SerializedName("href")
    val href: String?, // null
    @SerializedName("total")
    val total: Int // 37081054
) : Parcelable