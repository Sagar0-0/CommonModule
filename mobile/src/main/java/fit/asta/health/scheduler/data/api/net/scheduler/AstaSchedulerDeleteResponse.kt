package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstaSchedulerDeleteResponse(
    @SerializedName("data")
    val `data`: DataXXX,
    @SerializedName("status")
    val status: Status
) : Parcelable