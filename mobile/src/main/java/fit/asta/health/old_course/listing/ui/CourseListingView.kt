package fit.asta.health.old_course.listing.ui

import android.app.Activity
import android.view.View
import fit.asta.health.old_course.listing.adapter.listeners.OnCourseClickListener
import fit.asta.health.old_course.listing.data.CourseIndexData

interface CourseListingView {

    fun setContentView(activity: Activity): View?
    fun setAdapterClickListener(listener: OnCourseClickListener)

    fun changeState(state: State)

    sealed class State {
        class LoadCourses(val list: List<CourseIndexData>) : State()
        object Empty: State()
        class Error(val message: String): State()
    }
}