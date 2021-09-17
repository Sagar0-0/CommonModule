package fit.asta.health.course.details

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import fit.asta.health.course.details.adapter.CourseDetailsTabType
import fit.asta.health.course.details.data.CourseDetailsData
import fit.asta.health.course.details.data.CourseHeaderData
import fit.asta.health.course.details.data.OverViewData
import fit.asta.health.course.details.data.SessionData

interface CourseDetailsView {

    fun setContentView(activity: Activity, container: ViewGroup?): View?
    fun changeState(state: State)
    fun setUpViewPager(fragmentManager: FragmentManager)
    fun setTabType(tabType: CourseDetailsTabType)

    sealed class State {
        class LoadCourseDetails(val course: CourseDetailsData) : State()
        class LoadHeader(val header: CourseHeaderData) : State()
        class LoadSessions(val listSessions: List<SessionData>) : State()
        class LoadOverView(val overView: OverViewData) : State()
        class Error(val message: String) : State()
        object Empty : State()
    }
}