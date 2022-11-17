package fit.asta.health.thirdparty.spotify.model.net.me.episodes


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("added_at")
    val addedAt: String, // 2022-09-01T17:45:23Z
    @SerializedName("episode")
    val episode: Episode
) : Parcelable