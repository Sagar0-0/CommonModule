package fit.asta.health.schedule.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScheduleTimeData(
    var hour: Int? = null,
    var minute: Int? = null
) : Parcelable

@Parcelize
data class ScheduleSessionData(
    var day: String = "",
    var cycles: List<ScheduleTimeData> = listOf()
) : Parcelable

@Parcelize
data class ScheduleData(
    var uid: String = "",
    var tagId: String = "",
    var title: String = "",
    var description: String = "",
    var startDate: String = "",
    var courseDuration: Int = 0,
    var alertType: Int = 0,
    var priority: Int = 0,
    var remindBefore: Int = 0,
    var sessions: List<ScheduleSessionData> = listOf()
) : Parcelable