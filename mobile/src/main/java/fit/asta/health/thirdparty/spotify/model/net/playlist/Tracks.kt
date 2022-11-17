package fit.asta.health.thirdparty.spotify.model.net.playlist


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tracks(
    @SerializedName("href")
    val href: String, // https://api.spotify.com/v1/playlists/4AyeT23Lb3jjt0vDewAp4y/tracks
    @SerializedName("total")
    val total: Int // 17
) : Parcelable