package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsXXXX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/track/6sE0yPUWuIOlqJhsUCvOrg
) : Parcelable