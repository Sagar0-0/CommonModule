package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Vib(
    @SerializedName("pct")
    val pattern: String, // 1
    @SerializedName("sts")
    val status: Boolean // true
) : Parcelable
