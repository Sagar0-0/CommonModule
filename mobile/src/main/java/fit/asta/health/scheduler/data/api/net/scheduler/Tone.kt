package fit.asta.health.scheduler.data.api.net.scheduler


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