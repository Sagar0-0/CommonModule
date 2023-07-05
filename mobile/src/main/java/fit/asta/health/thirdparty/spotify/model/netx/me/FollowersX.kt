package fit.asta.health.thirdparty.spotify.model.netx.me


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowersX(
    @SerializedName("href")
    val href: String?, // string
    @SerializedName("total")
    val total: Int // 0
) : Parcelable