package fit.asta.health.thirdparty.spotify.model.net.me.player.recentlyplayed


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsXX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/album/5l2mNpKokiscks6SEIQnHe
) : Parcelable