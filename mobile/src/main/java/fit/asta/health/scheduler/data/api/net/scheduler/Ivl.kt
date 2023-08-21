package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ivl(
    @SerializedName("snu")
    var snoozeTime: Int, // 10
    @SerializedName("adv")
    val advancedReminder: Adv,
    @SerializedName("dur")
    var duration: Int, // 10
    @SerializedName("end")
    val isRemainderAtTheEnd: Boolean, // true
    @SerializedName("rep")
    var repeatableInterval: Rep,

    @SerializedName("sts")
    var status: Boolean,
    @SerializedName("stat")
    var staticIntervals: List<Stat>?,
    @SerializedName("vars")
    val variantIntervals: List<Stat>?,
    @SerializedName("vit")
    val isVariantInterval: Boolean // true
) : Parcelable