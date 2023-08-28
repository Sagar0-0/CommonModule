package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataXXX(
    @SerializedName("flag")
    val flag: Boolean, // true
    @SerializedName("msg")
    val msg: String // Deleted
) : Parcelable