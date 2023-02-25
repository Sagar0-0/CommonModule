package fit.asta.health.old.course.listing.viewmodel

import androidx.lifecycle.Observer
import fit.asta.health.old.course.listing.ui.CourseListingView

class CourseListingObserver(private val courseListingView: CourseListingView) :
    Observer<CourseListingAction> {
    override fun onChanged(action: CourseListingAction) {
        when (action) {
            is CourseListingAction.LoadCourses -> {
                val state = CourseListingView.State.LoadCourses(action.list)
                courseListingView.changeState(state)
            }
            CourseListingAction.Empty -> {
                val state = CourseListingView.State.Empty
                courseListingView.changeState(state)
            }

            is CourseListingAction.Error -> {
                val state = CourseListingView.State.Error(action.message)
                courseListingView.changeState(state)
            }
        }
    }
}