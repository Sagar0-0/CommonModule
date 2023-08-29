package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stat(
    @SerializedName("hr")
    val hours: String, // 10
    @SerializedName("mdy")
    val midDay: Boolean, // false
    @SerializedName("min")
    val minutes: String, // 2
    @SerializedName("name")
    val name: String, // water
    @SerializedName("tid")
    val id: Int // 0
) : Parcelable