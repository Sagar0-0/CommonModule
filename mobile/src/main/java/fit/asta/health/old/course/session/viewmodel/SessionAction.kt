package fit.asta.health.old.course.session.viewmodel

import fit.asta.health.old.course.session.data.SessionData

sealed class SessionAction {

    class LoadSession(val session: SessionData) : SessionAction()
    object Empty : SessionAction()
    class Error(val message: String) : SessionAction()

}