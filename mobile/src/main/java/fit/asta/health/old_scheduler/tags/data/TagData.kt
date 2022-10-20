package fit.asta.health.old_scheduler.tags.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleTagData(
    var uid: String = "",
    var tagName: String = "",
    var type: Int = 0,
    var url: String = "",
    var isSelected: Boolean = false
) : Parcelable