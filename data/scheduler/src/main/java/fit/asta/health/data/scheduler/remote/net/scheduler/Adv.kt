package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Adv(
    @SerializedName("sts")
    val status: Boolean, // true
    @SerializedName("time")
    var time: Int // 10
) : Parcelable