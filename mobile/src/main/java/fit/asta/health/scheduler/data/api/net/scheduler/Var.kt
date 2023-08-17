package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Var(
    @SerializedName("hr")
    val hr: String, // 10
    @SerializedName("mdy")
    val mdy: Boolean, // false
    @SerializedName("min")
    val min: String, // 3
    @SerializedName("name")
    val name: String, // sleep
    @SerializedName("tid")
    val tid: Int // 0
) : Parcelable