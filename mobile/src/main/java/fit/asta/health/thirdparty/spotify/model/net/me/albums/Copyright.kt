package fit.asta.health.thirdparty.spotify.model.net.me.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Copyright(
    @SerializedName("text")
    val text: String, // 2018 Ritviz
    @SerializedName("type")
    val type: String // C
) : Parcelable