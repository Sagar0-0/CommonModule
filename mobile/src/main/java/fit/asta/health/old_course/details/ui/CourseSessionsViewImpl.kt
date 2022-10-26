package fit.asta.health.old_course.details.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import fit.asta.health.R
import fit.asta.health.old_course.details.adapter.CourseDetailsTabType
import fit.asta.health.old_course.details.adapter.ModulesAdapter
import fit.asta.health.old_course.details.data.SessionData
import kotlinx.android.synthetic.main.sessions_fragment.view.*


class CourseSessionsViewImpl : CourseSessionsView {

    private var rootView: View? = null
    private var courseTabType: CourseDetailsTabType = CourseDetailsTabType.SessionsTab

    override fun setContentView(activity: Activity, container: ViewGroup?): View? {
        rootView = LayoutInflater.from(activity).inflate(
            R.layout.sessions_fragment, container,
            false
        )

        return rootView
    }

    override fun changeState(state: CourseSessionsView.State) {
        when (state) {
            is CourseSessionsView.State.LoadSessions -> {
                setAdapter(state.listSessions)
            }
            else -> {

            }
        }
    }

    override fun setTabType(tabType: CourseDetailsTabType) {
        courseTabType = tabType
    }

    private fun setAdapter(list: List<SessionData>) {
        rootView?.let {
            it.courseModulesRcView.layoutManager = LinearLayoutManager(it.context)
            it.courseModulesRcView.adapter = ModulesAdapter(it.context, "", list)
            //(it.courseModulesRcView.adapter as ModulesAdapter).updateList(list)
        }
    }
}