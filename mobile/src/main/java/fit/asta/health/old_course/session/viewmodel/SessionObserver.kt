package fit.asta.health.old_course.session.viewmodel

import androidx.lifecycle.Observer
import fit.asta.health.old_course.session.ui.SessionView

class SessionObserver(private val sessionView: SessionView) :
    Observer<SessionAction> {
    override fun onChanged(action: SessionAction) {
        when (action) {
            is SessionAction.LoadSession -> {
                val state = SessionView.State.LoadSession(action.session)
                sessionView.changeState(state)
            }
            SessionAction.Empty -> {
                val state = SessionView.State.Empty
                sessionView.changeState(state)
            }

            is SessionAction.Error -> {
                val state = SessionView.State.Error(action.message)
                sessionView.changeState(state)
            }
        }
    }
}