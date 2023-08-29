package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ivl(
    @SerializedName("snu")
    var snoozeTime: Int, // 10
    @SerializedName("adv")
    val advancedReminder: Adv,
    @SerializedName("sts")
    var statusEnd: Boolean,
    @SerializedName("end")
    var endAlarmTime: Time,
) : Parcelable