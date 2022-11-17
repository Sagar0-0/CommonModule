package fit.asta.health.thirdparty.spotify.model.net.albums


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Copyright(
    @SerializedName("text")
    val text: String, // (P) 2012 RCA Records, a division of Sony Music Entertainment
    @SerializedName("type")
    val type: String // P
) : Parcelable