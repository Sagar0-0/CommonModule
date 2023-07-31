package fit.asta.health.scheduler.model.net.spotify.common


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Followers(
    @SerializedName("href")
    val href: String?,
    @SerializedName("total")
    val total: Int
) : Parcelable