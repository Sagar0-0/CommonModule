package fit.asta.health.old.course.details.ui

import android.app.Activity
import android.view.View
import fit.asta.health.old.course.details.CourseDetailsActivity
import fit.asta.health.old.course.details.CourseDetailsView
import fit.asta.health.old.course.details.CourseDetailsViewPagerListener

interface CourseDetailsPagerView {

    fun setContentView(activity: Activity): View?
    fun changeState(state: CourseDetailsView.State)
    fun setUpViewPager(
        fragmentActivity: CourseDetailsActivity,
        listener: CourseDetailsViewPagerListener
    )
}