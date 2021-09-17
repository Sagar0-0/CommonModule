package fit.asta.health.course.session.viewmodel

import fit.asta.health.course.session.data.SessionData

sealed class SessionAction {

    class LoadSession(val session: SessionData) : SessionAction()
    object Empty : SessionAction()
    class Error(val message: String) : SessionAction()

}