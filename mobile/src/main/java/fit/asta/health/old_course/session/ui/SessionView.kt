package fit.asta.health.old_course.session.ui

import android.app.Activity
import android.view.View
import fit.asta.health.old_course.session.adapter.listeners.OnExerciseClickListener
import fit.asta.health.old_course.session.data.SessionData
import fit.asta.health.old_course.session.listners.OnSessionClickListener

interface SessionView {
    fun setContentView(activity: Activity): View?
    fun setAdapterClickListener(listener: OnExerciseClickListener)
    fun setSessionPlayClickListener(listener: OnSessionClickListener)
    fun changeState(state: State)

    sealed class State {
        class LoadSession(val session: SessionData) : State()
        object Empty : State()
        class Error(val message: String) : State()
    }
}