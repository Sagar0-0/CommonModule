package fit.asta.health.scheduler.model.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.scheduler.model.db.entity.AlarmEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstaSchedulerGetResponse(
    @SerializedName("data")
    val `data`: AlarmEntity,
    @SerializedName("status")
    val status: Status
) : Parcelable