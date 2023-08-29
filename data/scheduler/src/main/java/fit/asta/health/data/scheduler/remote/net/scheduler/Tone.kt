package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tone(
    @SerializedName("name")
    val name: String, // spring
    @SerializedName("type")
    val type: Int, // 1
    @SerializedName("uri")
    val uri: String // url
) : Parcelable