package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Time(
    @SerializedName("hr")
    var hours: Int,
    @SerializedName("min")
    var minutes: Int
) : Parcelable