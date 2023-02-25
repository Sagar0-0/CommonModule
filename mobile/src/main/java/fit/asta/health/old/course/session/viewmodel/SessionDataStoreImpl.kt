package fit.asta.health.old.course.session.viewmodel

import fit.asta.health.old.course.session.data.Exercise
import fit.asta.health.old.course.session.data.SessionData

class SessionDataStoreImpl : SessionDataStore {
    private var sessionData: SessionData = SessionData()

    override fun updateSession(sessionData: SessionData) {
        this.sessionData = sessionData
    }

    override fun getExercises(): List<Exercise> {
        return sessionData.exerciseList
    }

    override fun getExercise(position: Int): Exercise {
        return sessionData.exerciseList[position]
    }
}