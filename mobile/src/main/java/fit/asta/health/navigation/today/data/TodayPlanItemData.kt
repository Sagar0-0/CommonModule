package fit.asta.health.navigation.today.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

enum class TodayPlanItemType(val value: Int) {


    TodaySingleReminder(1),
    TodayDoubleReminder(2),
    TodayCourse(3),
    TodayWater(4),
    TodayAppointment(5);
    companion object {
        fun valueOf(value: Int) = values().first { it.value == value }
    }
}

@Parcelize
data class TodayPlanItemData(
    var uid: String = "",
    var courseId: String = "",
    var sessionId: String = "",
    var type: TodayPlanItemType = TodayPlanItemType.valueOf(1),
    var tag: String = "",
    var title: String = "",
    var subTitle: String = "",
    var urlType: Int = 1,
    var imageUrl: String = "",
    var time: Long = 0,
    var duration: Long = 0,
    var progress: Double = 0.0,
    var stat: Int = 0
) : Parcelable