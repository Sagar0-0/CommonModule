package fit.asta.health.scheduler.data.api.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.data.db.entity.AlarmEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstaSchedulerGetResponse(
    @SerializedName("data")
    val `data`: AlarmEntity,
    @SerializedName("status")
    val status: Status
) : Parcelable