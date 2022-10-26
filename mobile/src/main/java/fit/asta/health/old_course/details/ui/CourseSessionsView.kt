package fit.asta.health.old_course.details.ui

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import fit.asta.health.old_course.details.adapter.CourseDetailsTabType
import fit.asta.health.old_course.details.data.CourseHeaderData
import fit.asta.health.old_course.details.data.OverViewData
import fit.asta.health.old_course.details.data.SessionData

interface CourseSessionsView {

    fun setContentView(activity: Activity, container: ViewGroup?): View?
    fun changeState(state: State)
    fun setTabType(tabType: CourseDetailsTabType)

    sealed class State {
        class LoadHeader(val header: CourseHeaderData) : State()
        class LoadSessions(val listSessions: List<SessionData>) : State()
        class LoadOverView(val overView: OverViewData) : State()
        class Error(val message: String) : State()
        object Empty : State()
    }
}