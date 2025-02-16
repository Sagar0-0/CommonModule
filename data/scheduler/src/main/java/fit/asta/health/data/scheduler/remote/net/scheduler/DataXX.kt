package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataXX(
    @SerializedName("flag")
    val flag: Boolean = true, // true
    @SerializedName("id")
    val id: String = "63467d75491a98141d896f7b", // 63467d75491a98141d896f7b
    @SerializedName("msg")
    val msg: String = "Created" // Created
) : Parcelable