package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExternalUrlsX(
    @SerializedName("spotify")
    val spotify: String // https://open.spotify.com/album/4aawyAB9vmqN3uQ7FjRGTy
) : Parcelable