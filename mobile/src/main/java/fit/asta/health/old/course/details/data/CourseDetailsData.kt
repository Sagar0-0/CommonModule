package fit.asta.health.old.course.details.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CourseHeaderData(
    var pubDate: String = "",
    var title: String = "",
    var subTitle: String = "",
    var urlType: Int = 1,
    var url: String = "",
    var level: String = "",
    var duration: String = "",
    var lang: String = ""
) : Parcelable

@Parcelize
data class IntroData(
    var url: String = "",
    var title: String = "",
    var desc: String = ""
) : Parcelable

@Parcelize
data class KeyPointsData(
    var title: String = "",
    var points: List<String> = listOf()
) : Parcelable

@Parcelize
data class AgeGroupData(
    var from: Int = 0,
    var to: Int = 0
) : Parcelable

@Parcelize
data class AudienceData(
    var ageGroup: AgeGroupData = AgeGroupData(),
    var gender: String = "",
    var level: String = "",
    var preRequisites: List<String> = listOf(),
    var notFor: List<String> = listOf()
) : Parcelable

@Parcelize
data class ExpertData(
    var uid: String = "",
    var name: String = "",
    var expYears: String = "",
    var profession: String = "",
    var qualification: String = "",
    var about: String = "",
    var url: String = ""
) : Parcelable

@Parcelize
data class OverViewData(
    var intro: IntroData = IntroData(),
    var keyPoints: KeyPointsData = KeyPointsData(),
    var audience: AudienceData = AudienceData(),
    var experts: List<ExpertData> = listOf()
) : Parcelable

@Parcelize
data class SessionData(
    var uid: String = "",
    var title: String = "",
    var subTitle: String = "",
    var level: String = "",
    var duration: Int = 0,
    var calories: Int = 0,
    var intensity: Float = 0.0F,
    var desc: String = "",
    var precautions: List<String> = listOf()
) : Parcelable

@Parcelize
data class CourseDetailsData(
    var uid: String = "",
    var header: CourseHeaderData = CourseHeaderData(),
    var overview: OverViewData = OverViewData(),
    var sessions: List<SessionData> = listOf()
) : Parcelable
