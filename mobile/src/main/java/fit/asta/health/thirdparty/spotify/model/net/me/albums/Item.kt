package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("added_at")
    val addedAt: String, // 2021-01-26T10:28:08Z
    @SerializedName("album")
    val album: Album
) : Parcelable