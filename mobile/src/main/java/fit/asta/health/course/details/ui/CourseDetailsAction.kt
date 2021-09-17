package fit.asta.health.course.details.ui

import fit.asta.health.course.details.data.CourseDetailsData
import fit.asta.health.course.details.data.CourseHeaderData
import fit.asta.health.course.details.data.OverViewData
import fit.asta.health.course.details.data.SessionData


sealed class CourseDetailsAction {
    class LoadCourseDetails(val course: CourseDetailsData) : CourseDetailsAction()
    class LoadHeader(val header: CourseHeaderData) : CourseDetailsAction()
    class LoadSessions(val listSessions: List<SessionData>) : CourseDetailsAction()
    class LoadOverview(val overView: OverViewData) : CourseDetailsAction()
    class Error(val message: String) : CourseDetailsAction()
    object Empty : CourseDetailsAction()
}