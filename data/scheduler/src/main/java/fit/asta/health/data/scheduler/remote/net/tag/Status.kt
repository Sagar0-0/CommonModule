package fit.asta.health.data.scheduler.remote.net.tag


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    @SerializedName("code")
    val code: Int = 0, // 200
    @SerializedName("msg")
    val msg: String = "" // Successful
) : Parcelable