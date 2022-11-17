package fit.asta.health.thirdparty.spotify.model.net.me.shows


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("added_at")
    val addedAt: String, // 2022-09-12T13:01:46Z
    @SerializedName("show")
    val show: Show
) : Parcelable