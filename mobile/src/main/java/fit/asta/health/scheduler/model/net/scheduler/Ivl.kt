package fit.asta.health.scheduler.model.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ivl(
    @SerializedName("adv")
    val advancedReminder: Adv,
    @SerializedName("dur")
    var duration: Int, // 10
    @SerializedName("end")
    val isRemainderAtTheEnd: Boolean, // true
    @SerializedName("rep")
    var repeatableInterval: Rep,
    @SerializedName("snu")
    var snoozeTime: Int, // 10
     @SerializedName("sts")
    var status: Boolean,
    @SerializedName("stat")
    var staticIntervals: List<Stat>,
    @SerializedName("vars")
    val variantIntervals: List<Stat>,
    @SerializedName("vit")
    val isVariantInterval: Boolean // true
) : Parcelable