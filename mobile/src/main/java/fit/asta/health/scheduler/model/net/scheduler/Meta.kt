package fit.asta.health.scheduler.model.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meta(
    @SerializedName("cBy")
    val cBy: Int, // 1
    @SerializedName("cDate")
    val cDate: String, // 2022-09-01 19:54:17.7642655 +0530 IST m=+161.808671301
    @SerializedName("sync")
    val sync: Int, // 1
    @SerializedName("uDate")
    val uDate: String
) : Parcelable