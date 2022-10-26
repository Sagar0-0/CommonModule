package fit.asta.health.old_course.session.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SessionData(
    var uid: String = "",
    var imgUrl: String = "",
    var title: String = "",
    var author: String = "",
    var day: Int = 0,
    var totalDays: Int = 0,
    var progress: Int = 0,
    var level: String = "",
    var duration: Long = 0,
    var intensity: Float = 0.0F,
    var calories: Int = 0,
    var exerciseList: List<Exercise> = emptyList()
) : Parcelable

@Parcelize
data class Exercise(
    var uid: String = "",
    var title: String = "",
    var subTitle: String = "",
    var duration: Long = 0,
    var url: String = "",
    var style: String = "",
    var level: String = "",
    var intensity: String = "",
    var calories: Int = 0
) : Parcelable
