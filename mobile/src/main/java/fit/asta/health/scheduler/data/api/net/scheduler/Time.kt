package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    @SerializedName("hr")
    var hours: String, // 10
    @SerializedName("mdy")
    val midDay: Boolean, // true
    @SerializedName("min")
    var minutes: String // 12
) : Parcelable