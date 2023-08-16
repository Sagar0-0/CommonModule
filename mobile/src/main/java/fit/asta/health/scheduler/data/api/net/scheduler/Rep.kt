package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rep(
    @SerializedName("time")
    val time: Int, // 1
    @SerializedName("unit")
    val unit: String // 2
) : Parcelable