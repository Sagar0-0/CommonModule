package fit.asta.health.data.scheduler.remote.net.scheduler


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import fit.asta.health.data.scheduler.local.model.AlarmEntity

import kotlinx.parcelize.Parcelize

@Parcelize
data class AstaSchedulerGetListResponse(
    @SerializedName("data")
    val `data`: List<AlarmEntity>,
    @SerializedName("status")
    val status: Status
) : Parcelable