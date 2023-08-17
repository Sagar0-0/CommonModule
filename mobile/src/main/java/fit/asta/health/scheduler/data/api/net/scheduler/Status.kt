package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("msg")
    val msg: String // Successful
) : Parcelable