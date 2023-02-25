package fit.asta.health.old.course.details.ui

import androidx.lifecycle.Observer
import fit.asta.health.old.course.details.CourseDetailsView

class CourseDetailsObserver(private val viewCourseDetails: CourseDetailsView) :
    Observer<CourseDetailsAction> {
    override fun onChanged(action: CourseDetailsAction) {

        when (action) {
            is CourseDetailsAction.LoadCourseDetails -> {
                val state = CourseDetailsView.State.LoadCourseDetails(action.course)
                viewCourseDetails.changeState(state)
            }
            is CourseDetailsAction.LoadHeader -> {
                val state = CourseDetailsView.State.LoadHeader(action.header)
                viewCourseDetails.changeState(state)
            }
            is CourseDetailsAction.LoadSessions -> {
                val state = CourseDetailsView.State.LoadSessions(action.listSessions)
                viewCourseDetails.changeState(state)
            }
            is CourseDetailsAction.LoadOverview -> {
                val state = CourseDetailsView.State.LoadOverView(action.overView)
                viewCourseDetails.changeState(state)
            }
            is CourseDetailsAction.Error -> {
                val state = CourseDetailsView.State.Error(action.message)
                viewCourseDetails.changeState(state)
            }
            is CourseDetailsAction.Empty -> {
                val state = CourseDetailsView.State.Empty
                viewCourseDetails.changeState(state)
            }
        }
    }
}